package com.slemenceu.taptrackpc.taptrack.domain

import com.slemenceu.taptrackpc.taptrack.domain.models.ConnectionStatus
import com.slemenceu.taptrackpc.taptrack.domain.models.ServerConfig
import kotlinx.coroutines.flow.StateFlow

interface ServerRepository {
    suspend fun startServer(): ServerConfig
    val connectionState: StateFlow<ConnectionStatus>
    fun getDeviceName(): String
    fun getNetworkName(): String
}