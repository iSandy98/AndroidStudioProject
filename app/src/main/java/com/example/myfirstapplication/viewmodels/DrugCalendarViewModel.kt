// DrugCalendarViewModel.kt
package com.example.myfirstapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.DrugStatus
import com.example.myfirstapplication.classes.Prefs.userId
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class DrugCalendarViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _statuses = MutableStateFlow<Map<LocalDate, DrugStatus>>(emptyMap())
    val statuses: StateFlow<Map<LocalDate, DrugStatus>> = _statuses
    private val TAG = "DrugCalendarVM"

    fun loadMonth(yearMonth: YearMonth, patientPhone: String) {
        viewModelScope.launch {
            _statuses.value = emptyMap()

            try {
                // 1. Сначала находим документ пациента по полю "phone"
                val userQuery = db.collection("users")
                    .whereEqualTo("phone", patientPhone)
                    .get()
                    .await()

                if (userQuery.isEmpty) {
                    Log.w(TAG, "Пациент с телефоном $patientPhone не найден")
                    return@launch
                }

                val patientDoc = userQuery.documents.first()
                val patientUid = patientDoc.id
                Log.d(TAG, "Найден пациент uid=$patientUid")

                // 2. Границы диапазона
                val startInst = yearMonth.atDay(1)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                val endInst = yearMonth.atEndOfMonth()
                    .atTime(23, 59, 59)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()

                val lowerTs = Timestamp(startInst.epochSecond, startInst.nano)
                val upperTs = Timestamp(endInst.epochSecond, endInst.nano)
                Log.d(TAG, "Запрос расписания между $lowerTs и $upperTs")

                // 3. Запросим schedule уже по правильному uid
                val snapshot = db.collection("users")
                    .document(patientUid)
                    .collection("schedule")
                    .whereGreaterThanOrEqualTo("timestamp", lowerTs)
                    .whereLessThanOrEqualTo("timestamp", upperTs)
                    .get()
                    .await()

                Log.d(TAG, "Документов расписания: ${snapshot.size()}")

                // 4. Преобразуем в Map<LocalDate, DrugStatus>
                val byDate = snapshot.documents
                    .mapNotNull { doc ->
                        val ts = doc.getTimestamp("timestamp")
                        val taken = doc.getBoolean("taken")
                        if (ts != null && taken != null) {
                            val date = Instant.ofEpochSecond(ts.seconds, ts.nanoseconds.toLong())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            date to if (taken) DrugStatus.TAKEN else DrugStatus.MISSED
                        } else null
                    }
                    .groupBy({ it.first }, { it.second })
                    .mapValues { (_, list) ->
                        when {
                            list.all   { it == DrugStatus.TAKEN }  -> DrugStatus.TAKEN
                            list.all   { it == DrugStatus.MISSED } -> DrugStatus.MISSED
                            else                                    -> DrugStatus.PARTIAL
                        }
                    }

                _statuses.value = byDate
                Log.d(TAG, "loadMonth completed, total dates=${byDate.size}")

            } catch (e: Exception) {
                Log.e(TAG, "Error loading month for $patientPhone", e)
                _statuses.value = emptyMap()
            }
        }
    }
}