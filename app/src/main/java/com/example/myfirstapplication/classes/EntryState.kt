package com.example.myfirstapplication.classes

sealed class EntryState {
    object Idle : EntryState()
    object Loading : EntryState()
    object Success : EntryState()
    data class Error(val message: String) : EntryState()
}