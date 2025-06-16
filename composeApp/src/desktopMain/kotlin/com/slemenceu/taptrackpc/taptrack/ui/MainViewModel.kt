package com.slemenceu.taptrackpc.taptrack.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slemenceu.taptrackpc.taptrack.domain.ServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.InetAddress

class MainViewModel(
    private val repository: ServerRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()
    fun onEvent(event: MainUiEvent){
        when(event){
            is MainUiEvent.ConnectToServer -> TODO()
            MainUiEvent.StartServer -> viewModelScope.launch {
                startServer()
            }
        }
    }

    private suspend fun startServer(){
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        val passcode = repository.startServer()
        _uiState.value = _uiState.value.copy(
            passcode = passcode,
            isLoading = false
        )
    }
    init {
        viewModelScope.launch {
            repository.connectedIp.collect { ip ->
                ip?.let {
                    val ipAddress = ip.hostAddress.toString()
                    _uiState.value = _uiState.value.copy(
                        ipAddress = ipAddress,
                        isConnected = true
                    )
                }
            }
        }
    }
}