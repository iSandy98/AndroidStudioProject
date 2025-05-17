package com.example.myfirstapplication.classes

sealed class FeedbackState {
    object Idle : FeedbackState()
    object Loading : FeedbackState()
    object Success : FeedbackState()
    data class Error(val message: String) : FeedbackState()
}
