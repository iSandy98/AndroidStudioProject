package com.example.myfirstapplication.classes

data class Drug(
    val id: String = "",
    val name: String,
    val dose: String,
    val times: List<String> // Время приема: "Утро", "Обед" и т.д.
)