package com.slemenceu.taptrackpc.taptrack.data.repository

import com.slemenceu.taptrackpc.taptrack.domain.ServerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import java.net.InetAddress

class ServerRepoTest: ServerRepository {
    override suspend fun startServer(): String {
        delay(5000)
        return "4567"
    }

    override val connectedIp: StateFlow<InetAddress?>
        get() = TODO("Not yet implemented")
}