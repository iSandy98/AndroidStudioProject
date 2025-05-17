package com.example.myfirstapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.FeedbackState
import com.example.myfirstapplication.classes.Prefs
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _feedbackText = MutableStateFlow("")
    /** Текст отзыва */
    val feedbackText: StateFlow<String> = _feedbackText

    private val _feedbackState = MutableStateFlow<FeedbackState>(FeedbackState.Idle)
    /** Текущее состояние отправки */
    val feedbackState: StateFlow<FeedbackState> = _feedbackState

    private val _senderName = MutableStateFlow("")
    /** ФИО отправителя */
    val senderName: StateFlow<String> = _senderName

    init {
        loadSenderName()
    }

    private fun loadSenderName() {
        val userId = Prefs.userId
        if (userId.isNullOrBlank()) return

        viewModelScope.launch {
            try {
                val doc = db.collection("users")
                    .document(userId)
                    .get()
                    .await()

                if (doc.exists()) {
                    _senderName.value = doc.getString("fullName") ?: ""
                }
            } catch (e: Exception) {
                Log.e("FeedbackViewModel", "Ошибка загрузки ФИО отправителя", e)
            }
        }
    }

    /** Обновление текста отзыва */
    fun onFeedbackTextChange(text: String) {
        _feedbackText.value = text
    }

    /** Отправка отзыва в коллекцию "messages" */
    fun sendFeedback() {
        val message = _feedbackText.value.trim()
        if (message.isEmpty()) {
            _feedbackState.value = FeedbackState.Error("Сообщение не может быть пустым")
            return
        }
        _feedbackState.value = FeedbackState.Loading
        viewModelScope.launch {
            val data = mapOf(
                "message" to message,
                "senderFullName" to _senderName.value,
                "timestamp" to FieldValue.serverTimestamp()
            )
            db.collection("messages")
                .add(data)
                .addOnSuccessListener {
                    _feedbackState.value = FeedbackState.Success
                    // Очистить ввод после успешной отправки
                    _feedbackText.value = ""
                }
                .addOnFailureListener { e ->
                    _feedbackState.value = FeedbackState.Error(e.localizedMessage ?: "Ошибка отправки")
                }
        }
    }

    /** Сброс состояния на Idle */
    fun resetState() {
        _feedbackState.value = FeedbackState.Idle
    }
}