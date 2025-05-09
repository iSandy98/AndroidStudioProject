package com.example.myfirstapplication.classes

data class Drug(
    val name: String,
    val dosage: String,
    val isMorningTake: Boolean = false,
    val isMiddayTake: Boolean = false,
    val isEveningTake: Boolean = false,
    val isNightTake: Boolean = false,
)
