package com.example.myfirstapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.EntryState
import com.example.myfirstapplication.classes.Mood
import com.example.myfirstapplication.classes.Prefs
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WellbeingViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
    private val TAG = "WellbeingViewModel"

    private val _moodPosition = MutableStateFlow(4f)
    val moodPosition: StateFlow<Float> = _moodPosition

    private val _sleepDuration = MutableStateFlow(0f)
    val sleepDuration: StateFlow<Float> = _sleepDuration

    private val _sleepQuality = MutableStateFlow(0f)
    val sleepQuality: StateFlow<Float> = _sleepQuality

    private val _moodSaveState = MutableStateFlow<EntryState>(EntryState.Idle)
    val moodSaveState: StateFlow<EntryState> = _moodSaveState

    private val _sleepSaveState = MutableStateFlow<EntryState>(EntryState.Idle)
    val sleepSaveState: StateFlow<EntryState> = _sleepSaveState

    private val _moodHistory  = MutableStateFlow<List<Mood>>(emptyList())
    val moodHistory: StateFlow<List<Mood>> = _moodHistory

    private val _sleepHistory = MutableStateFlow<List<Mood>>(emptyList())
    val sleepHistory: StateFlow<List<Mood>> = _sleepHistory

    fun onMoodPositionChange(value: Float) {
        _moodPosition.value = value
    }

    fun onSleepDurationChange(value: Float) {
        _sleepDuration.value = value
    }

    fun onSleepQualityChange(value: Float) {
        _sleepQuality.value = value
    }

    fun saveMood() {
        val userId = Prefs.userId
        if (userId.isNullOrBlank()) {
            _moodSaveState.value = EntryState.Error("UserId not found")
            return
        }
        _moodSaveState.value = EntryState.Loading
        viewModelScope.launch {
            try {
                val today = LocalDate.now(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ISO_DATE)
                val docId = "${userId}_$today"
                val data = mapOf(
                    "userId" to userId,
                    "date" to today,
                    "moodLevel" to _moodPosition.value.toInt(),
                    "timestamp" to FieldValue.serverTimestamp()
                )
                db.collection("patient_feelings")
                    .document(docId)
                    .set(data, SetOptions.merge())
                    .await()

                _moodSaveState.value = EntryState.Success
            } catch (e: Exception) {
                Log.e("WellbeingViewModel", "Error saving mood", e)
                _moodSaveState.value = EntryState.Error(e.localizedMessage ?: "Ошибка сохранения")
            }
        }
    }

    fun saveSleep() {
        val userId = Prefs.userId
        if (userId.isNullOrBlank()) {
            _sleepSaveState.value = EntryState.Error("UserId not found")
            return
        }
        _sleepSaveState.value = EntryState.Loading
        viewModelScope.launch {
            try {
                val today = LocalDate.now(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ISO_DATE)
                val docId = "${userId}_$today"
                val data = mapOf(
                    "userId" to userId,
                    "date" to today,
                    "durationHours" to _sleepDuration.value,
                    "quality" to _sleepQuality.value.toInt(),
                    "timestamp" to FieldValue.serverTimestamp()
                )
                db.collection("patient_feelings")
                    .document(docId)
                    .set(data, SetOptions.merge())
                    .await()

                _sleepSaveState.value = EntryState.Success
            } catch (e: Exception) {
                Log.e("WellbeingViewModel", "Error saving sleep", e)
                _sleepSaveState.value = EntryState.Error(e.localizedMessage ?: "Ошибка сохранения")
            }
        }
    }

    /**
     * Загружает mood и sleep за последние 7 дней для текущего пользователя.
     */
    fun loadHistoryForPhone(patientPhone: String) {
        viewModelScope.launch {
            // 1. Ищем пользователя по телефону
            Log.d(TAG, "Ищем patientId по телефону $patientPhone")
            val userQuery = db.collection("users")
                .whereEqualTo("phone", patientPhone)
                .get()
                .await()

            if (userQuery.isEmpty) {
                Log.w(TAG, "Пользователь с телефоном $patientPhone не найден")
                return@launch
            }

            val patientUid = userQuery.documents.first().id
            Log.d(TAG, "Найден patientUid = $patientUid")

            try {
                // 2. Делаем запрос по найденному uid
                val today = LocalDate.now(ZoneId.systemDefault())
                val fromDate = today.minusDays(6)
                val fmt = DateTimeFormatter.ISO_DATE

                Log.d(TAG, "Запрос history для $patientUid с $fromDate по $today")

                val snapshot = db.collection("patient_feelings")
                    .whereEqualTo("userId", patientUid)
                    .whereGreaterThanOrEqualTo("date", fromDate.format(fmt))
                    .whereLessThanOrEqualTo("date", today.format(fmt))
                    .get()
                    .await()

                Log.d(TAG, "Получено документов feelings: ${snapshot.size()}")

                // 3. Собираем tempMap и формируем списки, как раньше
                val tempMap = mutableMapOf<String, Pair<Int?, Float?>>()
                snapshot.documents.forEach { doc ->
                    val date       = doc.getString("date")
                    val moodLevel  = doc.getLong("moodLevel")?.toInt()
                    val duration   = doc.getDouble("durationHours")?.toFloat()
                    Log.d(TAG, " • ${doc.id}: date=$date, mood=$moodLevel, dur=$duration")
                    if (date != null) {
                        val prev = tempMap[date]
                        tempMap[date] = Pair(
                            moodLevel ?: prev?.first,
                            duration  ?: prev?.second
                        )
                    }
                }

                val moodList  = mutableListOf<Mood>()
                val sleepList = mutableListOf<Mood>()
                for (i in 0..6) {
                    val d     = fromDate.plusDays(i.toLong())
                    val key   = d.format(fmt)
                    val label = d.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    val (m, sd) = tempMap[key] ?: Pair(null, null)
                    Log.d(TAG, " → $label ($key): mood=$m, sleep=$sd")
                    moodList += Mood(label, m ?: 0)
                    sleepList += Mood(label, sd?.toInt() ?: 0)
                }

                _moodHistory .value = moodList
                _sleepHistory.value = sleepList

                Log.d(TAG, "Готово moodHistory=$moodList")
                Log.d(TAG, "Готово sleepHistory=$sleepList")

            } catch (e: Exception) {
                Log.e(TAG, "Ошибка loadHistoryForPhone", e)
            }
        }
    }

    fun resetMoodState() {
        _moodSaveState.value = EntryState.Idle
    }
    fun resetSleepState() {
        _sleepSaveState.value = EntryState.Idle
    }
}