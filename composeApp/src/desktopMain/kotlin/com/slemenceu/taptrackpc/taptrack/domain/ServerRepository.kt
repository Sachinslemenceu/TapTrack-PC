package com.slemenceu.taptrackpc.taptrack.domain

import kotlinx.coroutines.flow.StateFlow
import java.net.InetAddress

interface ServerRepository {
    suspend fun startServer(): String
    val connectedIp: StateFlow<InetAddress?>
}