package com.example.myfirstapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.myfirstapplication.H3style


@Preview(showBackground = true)
@Composable
fun DrugsScreen() {
    var selectedTime by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(start = 40.dp, end = 40.dp, top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text("Список ваших назначений", style = H2style,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Row {
            Column(modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                TimeOfDayButton(
                    timeOfDay = "Утро",
                    isSelected = selectedTime == "Утро",
                    onClick = { selectedTime = "Утро" }
                )
                Spacer(Modifier.size(19.dp))
                TimeOfDayButton(
                    timeOfDay = "Вечер",
                    isSelected = selectedTime == "Вечер",
                    onClick = { selectedTime = "Вечер" }
                )
            }
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimeOfDayButton(
                    timeOfDay = "Обед",
                    isSelected = selectedTime == "Обед",
                    onClick = { selectedTime = "Обед" }
                )
                Spacer(Modifier.size(19.dp))
                TimeOfDayButton(
                    timeOfDay = "Ночь",
                    isSelected = selectedTime == "Ночь",
                    onClick = { selectedTime = "Ночь" }
                )
            }
        }
        Text(
            text = when (selectedTime) {
                "Утро" -> "Утренний прием"
                "Обед" -> "Дневной прием"
                "Вечер" -> "Вечерний прием"
                "Ночь" -> "Ночной прием"
                else -> "Выберите время приема"
            },
            style = H3style,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TimeOfDayButton(timeOfDay: String,
                     isSelected: Boolean,
                     onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier.height(36.dp).width(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected)
                colorResource(R.color.blue_main)
            else
                colorResource(R.color.white),
            contentColor = if (isSelected)
                colorResource(R.color.white)
            else
                colorResource(R.color.blue_main)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.blue_main)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text(text = timeOfDay)
    }
}

