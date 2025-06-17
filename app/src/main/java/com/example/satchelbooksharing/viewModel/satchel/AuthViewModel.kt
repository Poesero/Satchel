package com.example.satchelbooksharing.viewModel.satchel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<FirebaseAuth?>(
        if (firebaseAuth.currentUser != null) firebaseAuth else null
    )
    val authState = _authState.asStateFlow()

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = firebaseAuth
                        onResult(true, null)
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
        }
    }

    fun signup(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = firebaseAuth
                        onResult(true, null)
                    } else {
                        onResult(false, task.exception?.message)
                    }
                }
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _authState.value = null
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}
