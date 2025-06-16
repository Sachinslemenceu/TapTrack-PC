package com.slemenceu.taptrackpc.taptrack.ui

sealed class MainUiEvent {
    class ConnectToServer(val passcode: String): MainUiEvent()
    object StartServer: MainUiEvent()
}