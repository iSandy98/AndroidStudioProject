package com.example.myfirstapplication.classes

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ScheduleEntry(
    val drugId: String = "",
    val timeOfDay: String = "",
    val taken: Boolean = false,
    val timestamp: Any = FieldValue.serverTimestamp()
)

