package com.slemenceu.taptrackpc.taptrack.ui

import com.slemenceu.taptrackpc.taptrack.domain.models.ConnectionStatus

data class MainUiState(
    val connectionStatus: ConnectionStatus = ConnectionStatus.Idle,
    val isLoading: Boolean = false,
    val passcode: String = "",
    val serverIp: String = "",
    val serverPort: Int = 0,
    val deviceName: String = "",
    val networkName: String = ""
)
