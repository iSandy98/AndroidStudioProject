package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.R

@Composable
fun DoctorProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Профиль Врача",
            style = H3style,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(30.dp))
        Text("ФИО:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("Клепандина Юлия Васильевна", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Должность:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("Психиатр Нарколог", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Телефон:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("7 (924) 456 47 36", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Медицинская организация", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("ЦЭМП РБ№2", style = H4styleVer3)
        Spacer(Modifier.size(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {},
                modifier = Modifier.height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue_main),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Text(text = "Редактировать")
            }
        }
    }
}