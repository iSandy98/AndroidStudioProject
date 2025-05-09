package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myfirstapplication.classes.Drug
import com.example.myfirstapplication.classes.TimeOfDayTreatment
import com.example.myfirstapplication.screens.PatientProfileScreen



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PatientProfileScreen()
        }
    }
}
val selektra = Drug(name = "Селектра", dosage = "10мг", isMorningTake = true)
val depakin = Drug(name = "Депакин", dosage = "500мг", isMorningTake = true)
val database = TimeOfDayTreatment(listOf(selektra, depakin))


