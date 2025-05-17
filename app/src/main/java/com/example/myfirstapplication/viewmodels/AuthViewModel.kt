package com.example.myfirstapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState
    /**
     * Регистрация пользователя: ФИО, телефон, роль
     */
    fun registerUser(fullName: String, phone: String, role: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            db.collection("users")
                .whereEqualTo("phone", phone)
                .get()
                .addOnSuccessListener { query ->
                    if (query.isEmpty) {
                        val userData = mapOf(
                            "fullName" to fullName,
                            "phone" to phone,
                            "role" to role
                        )
                        db.collection("users")
                            .add(userData)
                            .addOnSuccessListener { docRef ->
                                _authState.value = AuthState.Success(docRef.id, role)
                            }
                            .addOnFailureListener { e ->
                                _authState.value = AuthState.Error(e.localizedMessage ?: "Ошибка записи")
                            }
                    } else {
                        _authState.value = AuthState.Error("Пользователь с таким номером уже существует")
                    }
                }
                .addOnFailureListener { e ->
                    _authState.value = AuthState.Error(e.localizedMessage ?: "Ошибка чтения")
                }
        }
    }

    /**
     * Вход пользователя по телефону
     */
    fun loginUser(phone: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            db.collection("users")
                .whereEqualTo("phone", phone)
                .get()
                .addOnSuccessListener { query ->
                    if (!query.isEmpty) {
                        val doc = query.documents[0]
                        val userId = doc.id
                        val role = doc.getString("role") ?: ""
                        _authState.value = AuthState.Success(userId, role)
                    } else {
                        _authState.value = AuthState.Error("Пользователь не найден")
                    }
                }
                .addOnFailureListener { e ->
                    _authState.value = AuthState.Error(e.localizedMessage ?: "Ошибка входа")
                }
        }
    }

    /**
     * Сброс состояния
     */
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

