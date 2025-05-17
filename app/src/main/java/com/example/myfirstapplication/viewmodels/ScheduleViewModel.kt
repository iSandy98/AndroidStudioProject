// ScheduleViewModel.kt
package com.example.myfirstapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.Prefs.userId
import com.example.myfirstapplication.classes.ScheduleEntry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _scheduleMap = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val scheduleMap: StateFlow<Map<String, Boolean>> = _scheduleMap

    fun loadSchedule(userId: String) {
        viewModelScope.launch {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("schedule")
                .get()
                .await()

            _scheduleMap.value = snapshot.documents
                .mapNotNull { doc ->
                    val drugId   = doc.getString("drugId")
                    val timeOfDay= doc.getString("timeOfDay")
                    val taken    = doc.getBoolean("taken")
                    if (drugId != null && timeOfDay != null && taken != null)
                        "${drugId}_$timeOfDay" to taken
                    else null
                }
                .toMap()
        }
    }

    fun saveEntry(userId: String, drugId: String, timeOfDay: String, taken: Boolean) {
        val docId = "${drugId}_$timeOfDay"
        val entry = ScheduleEntry(
            drugId   = drugId,
            timeOfDay= timeOfDay,
            taken    = taken
            // timestamp проставит Firestore на сервере
        )

        viewModelScope.launch {
            db.collection("users")
                .document(userId)
                .collection("schedule")
                .document(docId)
                .set(entry, SetOptions.merge())
                .await()

            _scheduleMap.update { old ->
                old + (docId to taken)
            }
        }
    }
}