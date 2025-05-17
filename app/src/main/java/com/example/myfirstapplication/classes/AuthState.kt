package com.example.myfirstapplication.classes

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: String, val role: String) : AuthState()
    data class Error(val message: String) : AuthState()
}