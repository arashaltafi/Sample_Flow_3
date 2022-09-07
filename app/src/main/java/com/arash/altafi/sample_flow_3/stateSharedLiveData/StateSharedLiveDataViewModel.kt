package com.arash.altafi.sample_flow_3.stateSharedLiveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateSharedLiveDataViewModel: ViewModel() {

    private val _liveData = MutableLiveData("Helo World")
    val liveData: LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow("Helo World")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun triggleLiveData() {
        _liveData.value = "Live Data"
    }

    fun triggleStateFlow() {
        _stateFlow.value = "State Flow"
    }

    fun triggleSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }

    fun triggleFlow(): Flow<String> {
        return flow {
            repeat(5) {
                emit("Item $it")
                delay(1000L)
            }

        }
    }

}