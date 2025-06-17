package com.example.satchelbooksharing.viewModel.satchel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satchelbooksharing.model.satchel.AppState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<AppState?>(null)
    val state: StateFlow<AppState?> = _state

    fun loadAppState() {
        viewModelScope.launch {
            delay(1500)
            val isLoggedIn = checkIfUserIsLoggedIn()
            _state.value = AppState(isUserLoggedIn = isLoggedIn)
        }
    }

    private fun checkIfUserIsLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}
