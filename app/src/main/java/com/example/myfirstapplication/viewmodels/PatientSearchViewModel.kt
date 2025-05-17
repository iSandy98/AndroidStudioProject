package com.example.myfirstapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class PatientSearchViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    // Текущий поисковый запрос
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // Список найденных профилей
    private val _searchResults = MutableStateFlow<List<UserProfile>>(emptyList())
    val searchResults: StateFlow<List<UserProfile>> = _searchResults.asStateFlow()

    // Флаг загрузки
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Возможная ошибка
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * Обновить запрос и выполнить поиск.
     */
    fun updateQuery(newQuery: String) {
        _query.value = newQuery.trim()
        searchPatients(_query.value)
    }

    /**
     * Поиск пациентов в Firestore по полю fullName.
     * Реализовано с использованием диапазона startAt и endAt для префиксного поиска.
     */
    private fun searchPatients(searchText: String) {
        if (searchText.isEmpty()) {
            // Очистить результаты при пустом запросе
            _searchResults.value = emptyList()
            _error.value = null
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // Firestore requires an index on fullName and ordering
                val queryRef = db.collection("users")
                    .orderBy("fullName")
                    .startAt(searchText)
                    .endAt(searchText + "\uf8ff")

                val querySnapshot = queryRef.get().await()
                val results = querySnapshot.documents.mapNotNull { doc ->
                    doc.getString("fullName")?.let { fullName ->
                        UserProfile(
                            fullName = fullName,
                            birthDate = doc.getString("birthDate") ?: "",
                            phone = doc.getString("phone") ?: "",
                            address = doc.getString("address") ?: "",
                            doctor = doc.getString("doctor") ?: "",
                            diagnosis = doc.getString("diagnosis") ?: "",
                            bloodGroup = doc.getString("bloodGroup") ?: ""
                        )
                    }
                }

                _searchResults.value = results

            } catch (e: Exception) {
                Log.e("PatientSearchVM", "Ошибка поиска пациентов", e)
                _error.value = "Не удалось выполнить поиск: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
