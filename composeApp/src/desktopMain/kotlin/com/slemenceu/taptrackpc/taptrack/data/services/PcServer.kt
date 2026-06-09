package com.slemenceu.taptrackpc.taptrack.data.services

import com.slemenceu.taptrackpc.taptrack.domain.models.ConnectionStatus
import com.slemenceu.taptrackpc.taptrack.domain.models.ServerConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.awt.MouseInfo
import java.awt.Robot
import java.awt.event.InputEvent
import java.io.DataInputStream
import java.net.*

class PcServer {
    private val TCP_PORT = 9998
    private val UDP_PORT = 9999

    private val robot = Robot().apply {
        autoDelay = 0
        isAutoWaitForIdle = false
    }

    private var isClientConnected = false
    private var trustedClientIp: InetAddress? = null

    private val _connectionState = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Idle)
    val connectionState = _connectionState.asStateFlow()

    private var serverJob: Job? = null

    // Optimization: Track mouse position internally to avoid frequent AWT calls


    private var currentMouseX: Int = 0
    private var currentMouseY: Int = 0

    fun startServer(passcode: String): ServerConfig {
        val localIp = getLocalNetworkIp()

        serverJob = CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            launch { runTcpControlChannel(passcode) }
            launch { runUdpMotionChannel() }
        }

        return ServerConfig(ip = localIp, port = TCP_PORT, passcode = passcode)
    }

    private suspend fun CoroutineScope.runTcpControlChannel(passcode: String) {
        val serverSocket = ServerSocket(TCP_PORT)

        serverSocket.use { server ->
            while (isActive) {
                try {
                    val client = server.accept()
                    client.tcpNoDelay = true
                    handleTcpClient(client)
                } catch (e: Exception) {
                    if (isActive) {
                        _connectionState.value = ConnectionStatus.Error("TCP Accept Error: ${e.message}")
                        println("[TCP] Accept Error: ${e.message}")
                    }
                }
            }
        }
    }

    private fun handleTcpClient(client: Socket) {
        client.use {
            val inputStream = DataInputStream(it.getInputStream())
            trustedClientIp = it.inetAddress
            isClientConnected = true
            _connectionState.value = ConnectionStatus.Connected(it.inetAddress.hostAddress)

            try {
                while (isClientConnected) {
                    when (val command = inputStream.readByte().toInt()) {
                        1 -> { // CLICK
                            val modifier = inputStream.readByte().toInt()
                            val mask = if (modifier == 1) InputEvent.BUTTON3_DOWN_MASK else InputEvent.BUTTON1_DOWN_MASK
                            robot.mousePress(mask)
                            robot.mouseRelease(mask)
                        }
                        95 -> { // HEARTBEAT PING
                            val pong = byteArrayOf(96)
                            it.getOutputStream().write(pong)
                            it.getOutputStream().flush()
                        }
                    }
                }
            } catch (e: Exception) {
                println("[TCP] Connection ended/error: ${e.message}")
            } finally {
                isClientConnected = false
                trustedClientIp = null
                _connectionState.value = ConnectionStatus.Disconnected
            }
        }
    }

    private fun CoroutineScope.runUdpMotionChannel() {
        val udpSocket = DatagramSocket(UDP_PORT)
        val buffer = ByteArray(9)
        val packet = DatagramPacket(buffer, buffer.size)

        udpSocket.use { socket ->
            // Initialize mouse position before the loop
            val initialLocation = MouseInfo.getPointerInfo().location
            currentMouseX = initialLocation.x
            currentMouseY = initialLocation.y

            while (isActive) {
                try {
                    socket.receive(packet)
                    if (!isClientConnected || packet.address != trustedClientIp) continue

                    when (packet.data[0].toInt()) {
                        0 -> { // MOVE command
                            val dx = bytesToInt(packet.data, 1)
                            val dy = bytesToInt(packet.data, 5)
                            
                            // Update and move to the new absolute position
                            currentMouseX += dx
                            currentMouseY += dy
                            robot.mouseMove(currentMouseX, currentMouseY)
                        }
                        2 -> { // SCROLL command
                            val scrollAmount = bytesToInt(packet.data, 1)
                            robot.mouseWheel(scrollAmount)
                        }
                        95 -> { // LATENCY_PING command
                            socket.send(packet)
                        }
                    }
                } catch (e: Exception) {
                    if (isActive) {
                        _connectionState.value = ConnectionStatus.Error("UDP Error: ${e.message}")
                        println("[UDP] Error: ${e.message}")
                    }
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
        _connectionState.value = ConnectionStatus.Idle
    }
}