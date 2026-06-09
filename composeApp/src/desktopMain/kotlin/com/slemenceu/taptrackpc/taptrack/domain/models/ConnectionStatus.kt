package com.slemenceu.taptrackpc.taptrack.domain.models

sealed class ConnectionStatus {
    object Idle : ConnectionStatus()
    data class Connected(val clientIp: String) : ConnectionStatus()
    object Disconnected : ConnectionStatus()
    data class Error(val message: String) : ConnectionStatus()
}
