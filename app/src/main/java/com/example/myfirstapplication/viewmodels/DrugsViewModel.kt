package com.example.myfirstapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.Drug
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * ViewModel для CRUD операций над лекарствами пользователя.
 * Использует телефон для поиска реального documentId пользователя,
 * затем оперирует под-коллекцией users/{docId}/drugs/{drugId}.
 */
@HiltViewModel
class DrugsViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _drugs = MutableStateFlow<List<Drug>>(emptyList())
    val drugs: StateFlow<List<Drug>> = _drugs

    private val TAG = "DrugsViewModel"

    /**
     * Помогает получить documentId пользователя по номеру телефона.
     * Возвращает null, если пользователь не найден.
     */
    private suspend fun getUserDocIdByPhone(phone: String): String? {
        return try {
            val query = db.collection("users")
                .whereEqualTo("phone", phone)
                .get()
                .await()
            query.documents.firstOrNull()?.id
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка поиска пользователя по телефону", e)
            null
        }
    }

    /**
     * Загружает все препараты для пользователя с данным телефоном.
     */
    fun loadDrugs(phone: String) {
        viewModelScope.launch {
            val userId = getUserDocIdByPhone(phone)
            if (userId == null) {
                Log.e(TAG, "Пользователь с телефоном $phone не найден")
                _drugs.value = emptyList()
                return@launch
            }

            try {
                val snapshot = db.collection("users")
                    .document(userId)
                    .collection("drugs")
                    .get()
                    .await()

                // Преобразуем каждый документ в Drug, сохраняя его id
                val list = snapshot.documents.map { doc ->
                    Drug(
                        id    = doc.id,
                        name  = doc.getString("name") ?: "",
                        dose  = doc.getString("dose") ?: "",
                        times = doc.get("times") as? List<String> ?: emptyList()
                    )
                }
                _drugs.value = list
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка загрузки лекарств", e)
                _drugs.value = emptyList()
            }
        }
    }

    /**
     * Добавляет новый препарат в под-коллекцию drugs пользователя, найденного по телефону.
     * onResult(true) — успех, onResult(false) — ошибка
     */
    fun addDrug(
        phone: String,
        drug: Drug,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val userId = getUserDocIdByPhone(phone)
            if (userId == null) {
                Log.e(TAG, "Пользователь с телефоном $phone не найден")
                onResult(false)
                return@launch
            }
            try {
                val col = db.collection("users")
                    .document(userId)
                    .collection("drugs")
                col.add(
                    mapOf(
                        "name"  to drug.name,
                        "dose"  to drug.dose,
                        "times" to drug.times
                    )
                ).await()
                onResult(true)
                // спустя добавление, обновим локальный список
                loadDrugs(phone)
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при добавлении препарата", e)
                onResult(false)
            }
        }
    }

    /**
     * Обновляет существующий препарат (по drug.id) в под-коллекции пользователя.
     * onResult(true) — успех, onResult(false) — ошибка
     */
    fun updateDrug(
        phone: String,
        drug: Drug,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val userId = getUserDocIdByPhone(phone)
            if (userId == null) {
                Log.e(TAG, "Пользователь с телефоном $phone не найден")
                onResult(false)
                return@launch
            }
            try {
                db.collection("users")
                    .document(userId)
                    .collection("drugs")
                    .document(drug.id)
                    .set(
                        mapOf(
                            "name"  to drug.name,
                            "dose"  to drug.dose,
                            "times" to drug.times
                        ),
                        SetOptions.merge()
                    )
                    .await()
                onResult(true)
                // после апдейта — подгрузим свежие данные
                loadDrugs(phone)
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при обновлении препарата", e)
                onResult(false)
            }
        }
    }

    /**
     * Удаляет препарат по его documentId из Firestore для пользователя.
     * onResult(true) — успех, onResult(false) — ошибка
     */
    fun deleteDrug(
        phone: String,
        drugId: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val userId = getUserDocIdByPhone(phone)
            if (userId == null) {
                Log.e(TAG, "Пользователь с телефоном $phone не найден")
                onResult(false)
                return@launch
            }
            try {
                db.collection("users")
                    .document(userId)
                    .collection("drugs")
                    .document(drugId)
                    .delete()
                    .await()
                onResult(true)
                // после удаления — перезагружаем список
                loadDrugs(phone)
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при удалении препарата", e)
                onResult(false)
            }
        }
    }
}