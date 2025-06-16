package com.slemenceu.taptrackpc.taptrack.data.services

import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.MouseInfo
import java.awt.Robot
import java.awt.event.MouseEvent
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress



class UDPServerService {

    private var trustedClientIp: InetAddress? = null

    private lateinit var socket: DatagramSocket

    val clientConnected: MutableStateFlow<InetAddress?> = MutableStateFlow(null)

    suspend fun startServer(): String {
        val port = 9999
        socket = DatagramSocket(port)
        val robot = Robot()
        val buffer = ByteArray(9)
        val passcode = generatePasscode()
        println("Server started. Pairing code: $passcode")

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                val result = handlePacket(packet, robot, passcode)
                if (result != null && trustedClientIp == null) {
                    trustedClientIp = result
                    clientConnected.value = result
                }
            }
        }

        return passcode
    }
    private suspend fun handlePacket(
        packet: DatagramPacket,
        robot: Robot,
        verifyCode: String,
    ): InetAddress? {
        val data = packet.data
        val commandType = data[0].toInt()

        if (commandType == 99) {
            val receivedPasscode = bytesToInt(data.sliceArray(1..4)).toString()
            println("Passcode received: $receivedPasscode")

            return if (receivedPasscode == verifyCode) {
                sendConfirmation(socket, packet, true)
                println("Client connected: ${packet.address.hostAddress}")
                packet.address
            } else {
                sendConfirmation(socket, packet, false)
                println("Wrong passcode attempt from ${packet.address}")
                null
            }
        }

        if (trustedClientIp == null || packet.address != trustedClientIp) {
            println("Unauthorized packet from ${packet.address}")
            return null
        }

        when (commandType) {
            0 -> moveMouse(robot, data)
            1 -> click(robot, MouseEvent.BUTTON1_DOWN_MASK)
            2 -> click(robot, MouseEvent.BUTTON3_DOWN_MASK)
            3 -> {
                click(robot, MouseEvent.BUTTON1_DOWN_MASK)
                delay(100)
                click(robot, MouseEvent.BUTTON1_DOWN_MASK)
            }
        }

        return trustedClientIp
    }

    private fun moveMouse(robot: Robot, data: ByteArray) {
        val dx = bytesToInt(data.sliceArray(1..4))
        val dy = bytesToInt(data.sliceArray(5..8))
        val current = MouseInfo.getPointerInfo().location
        robot.mouseMove(current.x + dx, current.y + dy)
    }

    private fun click(robot: Robot, mask: Int) {
        robot.mousePress(mask)
        robot.mouseRelease(mask)
    }

    private fun sendConfirmation(
        socket: DatagramSocket,
        packet: DatagramPacket,
        isValid: Boolean
    ) {
        val response = byteArrayOf(if (isValid) 100 else 101)
        val confirmPacket = DatagramPacket(response, response.size, packet.address, packet.port)
        socket.send(confirmPacket)
    }

    private fun bytesToInt(bytes: ByteArray): Int {
        return (bytes[0].toInt() shl 24) or
                ((bytes[1].toInt() and 0xFF) shl 16) or
                ((bytes[2].toInt() and 0xFF) shl 8) or
                (bytes[3].toInt() and 0xFF)
    }

    private fun generatePasscode(): String = (1000..9999).random().toString()
}






//object UDPServer{
//
//    var mainPasscode = ""
//    fun runServer() = runBlocking {
//        val port = 9999
//        val socket = DatagramSocket(port)
//        val robot = Robot()
//        val buffer = ByteArray(9)
//        var trustedClientIp: InetAddress? = null
//        val latestPasscode = generatePasscode()
//        mainPasscode = latestPasscode
//        println("The paring Code is $latestPasscode")
//        while (true){
//            val packet = DatagramPacket(buffer, buffer.size)
//            socket.receive(packet)
//            launch(Dispatchers.IO) {
//                val result = handlePacket(packet,robot, latestPasscode,socket,trustedClientIp)
//                if (result != null && trustedClientIp == null){
//                    trustedClientIp = result
//                }
//            }
//        }
//    }
//
//    private suspend fun handlePacket(
//        packet: DatagramPacket,
//        robot: Robot,
//        verifyCode: String,
//        socket: DatagramSocket,
//        trustedClientIp: InetAddress?
//    ): InetAddress? {
//        val data = packet.data
//        val commandType = data[0].toInt()
//        println("$commandType")
//
////    To get the passcode from the user
//        if (commandType == 99) {
//            val passcode = bytesToInt(data.sliceArray(1..5)).toString()
//            println("passcode received is : $passcode")
//            return if (passcode == verifyCode) {
//                println("Device is connected at ${packet.address.hostAddress}")
//                sendConfirmation(socket,packet,true)
//                packet.address
//            } else {
//                println("Invalid Login Attempt Ip address isnt valid")
//                sendConfirmation(socket,packet,false)
//                null
//            }
//        }
////    to check if the packet is sent by the authorized Ip
//        if (trustedClientIp == null || packet.address != trustedClientIp){
//            println("Unauthorized packet has been received")
//            return null
//        }
//        when (commandType){
//            0 -> {
//                val dx = bytesToInt(data.sliceArray(1..4))
//                val dy = bytesToInt(data.sliceArray(5..8))
//                val currentMouseLocation = MouseInfo.getPointerInfo().location
//                val newx = currentMouseLocation.x + dx
//                val newy = currentMouseLocation.y + dy
//                robot.mouseMove(newx, newy)
//                println(" mouse is moved")
//            }
//            1 -> { // Left click
//                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
//                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
//                println("Left click")
//            }
//
//            2 -> { // Right click
//                robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
//                robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
//                println("Right click")
//            }
//
//            3 -> { // Double Tap
//                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
//                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
//                delay(100)
//                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
//                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
//                println("Doube Tap")
//            }
//
//            else -> println("Unknown command type: $commandType")
//        }
//        return trustedClientIp
//    }
//
//    // Converts 4 bytes to Int (big endian)
//    private fun bytesToInt(bytes: ByteArray): Int {
//        return (bytes[0].toInt() shl 24) or
//                ((bytes[1].toInt() and 0xFF) shl 16) or
//                ((bytes[2].toInt() and 0xFF) shl 8) or
//                (bytes[3].toInt() and 0xFF)
//    }
//
//    // Generate 4 digit passcode
//    private fun generatePasscode(): String {
//        val passcode = (1000..9999).random().toString()
//        return passcode
//    }
//
//    private fun sendConfirmation(socket: DatagramSocket,packet: DatagramPacket, isValid: Boolean){
//        val confirmBuffer = ByteArray(1)
//        confirmBuffer[0] = if (isValid) 100 else 101
//        val confirmPacket = DatagramPacket(confirmBuffer,confirmBuffer.size,packet.address,packet.port)
//        socket.send(confirmPacket)
//    }
//
//}