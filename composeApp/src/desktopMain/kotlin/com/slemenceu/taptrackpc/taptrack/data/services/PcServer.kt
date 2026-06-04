

package com.slemenceu.taptrackpc.taptrack.data.services

import com.slemenceu.taptrackpc.taptrack.domain.models.ServerConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.awt.MouseInfo
import java.awt.Robot
import java.awt.event.InputEvent
import java.io.DataInputStream
import java.net.*

class ServerService {
    private val TCP_PORT = 9998
    private val UDP_PORT = 9999

    private val robot = Robot().apply {
        autoDelay = 0
        isAutoWaitForIdle = false
    }

    private var isClientConnected = false
    private var trustedClientIp: InetAddress? = null
    val connectionState = MutableStateFlow("Idle")

    private var serverJob: Job? = null

    fun startServer(passcode: String): ServerConfig {
        val localIp = getLocalNetworkIp()

        // We store the Job so we can cancel the whole server later
        serverJob = CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            // By making these extension functions, 'isActive' becomes available inside them
            launch { runTcpControlChannel(passcode) }
            launch { runUdpMotionChannel() }
        }

        return ServerConfig(ip = localIp, port = TCP_PORT, passcode = passcode)
    }

    /**
     * Added 'CoroutineScope.' to make isActive work
     */
    private suspend fun CoroutineScope.runTcpControlChannel(passcode: String) {
        val serverSocket = ServerSocket(TCP_PORT)
        println("[TCP] Listening on $TCP_PORT")

        // Use 'use' to ensure the server socket closes if the coroutine is cancelled
        serverSocket.use { server ->
            while (isActive) {
                try {
                    // accept() is blocking, but in Dispatchers.IO it's okay.
                    // To make it fully cancellable, we can set a timeout or check isActive
                    val client = server.accept()
                    client.tcpNoDelay = true
                    handleTcpClient(client)
                } catch (e: Exception) {
                    if (isActive) println("[TCP] Accept Error: ${e.message}")
                }
            }
        }
    }

    private fun handleTcpClient(client: Socket) {
        client.use {
            val inputStream = DataInputStream(it.getInputStream())
            trustedClientIp = it.inetAddress
            isClientConnected = true
            connectionState.value = "Connected: ${it.inetAddress.hostAddress}"

            try {
                // Command Loop - stays here as long as client is connected
                while (isClientConnected) {
                    val command = inputStream.readByte().toInt()
                    println(command)
                    when (command) {

                        1 -> { // CLICK
                            println("[TCP] Received CLICK command")
                            val modifier = inputStream.readByte().toInt()
                            val mask = if (modifier == 1) InputEvent.BUTTON3_DOWN_MASK else InputEvent.BUTTON1_DOWN_MASK
                            robot.mousePress(mask)
                            robot.mouseRelease(mask)
                        }
                        95 -> { // HEARTBEAT PING

                            println("[TCP] Received PING")

                            val pong = byteArrayOf(96)

                            it.getOutputStream().write(pong)
                            it.getOutputStream().flush()
                        }
                        // Add Case 3 (Key) and 4 (Text) here...
                    }
                }
            } catch (e: Exception) {
                println("[TCP] Connection ended: ${e.message}")
            } finally {
                isClientConnected = false
                trustedClientIp = null
                connectionState.value = "Disconnected"
            }
        }
    }

    /**
     * Added 'CoroutineScope.' to make isActive work
     */
    private fun CoroutineScope.runUdpMotionChannel() {
        val udpSocket = DatagramSocket(UDP_PORT)
        val buffer = ByteArray(9)
        val packet = DatagramPacket(buffer, buffer.size)

        udpSocket.use { socket ->
            println("[UDP] Listening on $UDP_PORT")
            while (isActive) {
                try {
                    socket.receive(packet)
                    if (!isClientConnected || packet.address != trustedClientIp) continue

                    val command = packet.data[0].toInt()

                    when (command) {
                        0 -> { // MOVE command
                            val dx = bytesToInt(packet.data, 1)
                            val dy = bytesToInt(packet.data, 5)
                            val current = MouseInfo.getPointerInfo().location
                            robot.mouseMove(current.x + dx, current.y + dy)
                        }

                        95 -> { // LATENCY_PING command
                            // REFLECTION:
                            // The 'packet' object now contains the Android IP and Port.
                            // We simply send it back immediately.
                            socket.send(packet)
                        }
                    }
                } catch (e: Exception) {
                    if (isActive) println("[UDP] Error: ${e.message}")
                }
            }
        }
    }

    private fun bytesToInt(bytes: ByteArray, offset: Int): Int {
        return (bytes[offset].toInt() shl 24) or
                ((bytes[offset + 1].toInt() and 0xFF) shl 16) or
                ((bytes[offset + 2].toInt() and 0xFF) shl 8) or
                (bytes[offset + 3].toInt() and 0xFF)
    }

    private fun getLocalNetworkIp(): String {
        return try {
            DatagramSocket().use { s ->
                s.connect(InetAddress.getByName("8.8.8.8"), 10002)
                s.localAddress.hostAddress
            }
        } catch (e: Exception) { "127.0.0.1" }
    }

    fun stopServer() {
        serverJob?.cancel()
        isClientConnected = false
    }
}