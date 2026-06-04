package com.slemenceu.taptrackpc.taptrack.data.repository

import com.slemenceu.taptrackpc.taptrack.data.services.ServerService
import com.slemenceu.taptrackpc.taptrack.domain.ServerRepository
import com.slemenceu.taptrackpc.taptrack.domain.models.ServerConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress

class ServerRepositoryImpl(
    private val serverService: ServerService
) : ServerRepository {
    override suspend fun startServer(): ServerConfig {
        println("Server is started")
        return serverService.startServer("1234")
    }

    override val connectedIp: StateFlow<InetAddress?>
        get() = InetAddress.getByName("1.2.2.") as StateFlow<InetAddress?>

    override fun getDeviceName(): String {
        return try {
            InetAddress.getLocalHost().hostName
        } catch (e: Exception) {
            "Unknown Device"
        }
    }

    override fun getNetworkName(): String {
        return try {
            // Run the Windows command to get active Wi-Fi interface details
            val process = ProcessBuilder("netsh", "wlan", "show", "interfaces").start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            var ssid = "Not Connected to Wi-Fi"

            while (reader.readLine().also { line = it } != null) {
                // Windows output usually looks like "SSID                   : MyHomeNetwork"
                if (line!!.contains("SSID") && !line!!.contains("BSSID")) {
                    ssid = line!!.substringAfter(":").trim()
                    break // Stop reading once we find the broadcasted network name
                }
            }
            process.waitFor()
            ssid
        } catch (e: Exception) {
            "Error fetching network name: ${e.message}"
        }
    }
}