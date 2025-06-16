package com.slemenceu.taptrackpc.taptrack.data.repository

import com.slemenceu.taptrackpc.taptrack.data.services.UDPServerService
import com.slemenceu.taptrackpc.taptrack.domain.ServerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import java.net.InetAddress

class ServerRepositoryImpl(
    private val serverService: UDPServerService
): ServerRepository {
    override suspend fun startServer(): String {
        println("Server is started")
        return serverService.startServer()
    }

    override val connectedIp: StateFlow<InetAddress?>
        get() = serverService.clientConnected
}