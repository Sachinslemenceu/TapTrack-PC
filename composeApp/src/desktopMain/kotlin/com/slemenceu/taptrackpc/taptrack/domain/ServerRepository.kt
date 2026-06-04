package com.slemenceu.taptrackpc.taptrack.domain

import com.slemenceu.taptrackpc.taptrack.domain.models.ServerConfig
import kotlinx.coroutines.flow.StateFlow
import java.net.InetAddress

interface ServerRepository {
    suspend fun startServer(): ServerConfig
    val connectedIp: StateFlow<InetAddress?>
    fun getDeviceName(): String
    fun getNetworkName(): String
}