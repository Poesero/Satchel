package com.example.satchelbooksharing.viewModel.satchel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satchelbooksharing.model.satchel.AppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<AppState?>(null)
    val state: StateFlow<AppState?> = _state

    fun loadAppState() {
        viewModelScope.launch {
            delay(5000)

            val userLoggedIn = checkIfUserIsLoggedIn()

            if (userLoggedIn) {
                _state.value = AppState(isUserLoggedIn = true)
            } else {
                _state.value = AppState(isUserLoggedIn = false, showContinueButton = true)
            }
        }
    }

    private fun checkIfUserIsLoggedIn(): Boolean {
        return false
        //En el futuro esto debera consultar con Firebase
    }
}