package com.example.myfirstapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.NotificationItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        fetchNotifications()
    }

    /**
     * Загрузка уведомлений из коллекции "messages"
     */
    fun fetchNotifications() {
        _loading.value = true
        viewModelScope.launch {
            db.collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { snapshot ->
                    val items = snapshot.documents.map { doc ->
                        val sender = doc.getString("senderFullName") ?: ""
                        val msg = doc.getString("message") ?: ""
                        val tsDate = doc.getTimestamp("timestamp")?.toDate()
                        val formatted = tsDate?.let {
                            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(it)
                        }
                        NotificationItem(sender, msg, formatted)
                    }
                    _notifications.value = items
                    _loading.value = false
                }
                .addOnFailureListener { e ->
                    _error.value = e.localizedMessage ?: "Ошибка загрузки"
                    _loading.value = false
                }
        }
    }
}