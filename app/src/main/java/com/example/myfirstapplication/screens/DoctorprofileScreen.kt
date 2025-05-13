package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.myfirstapplication.H1styleVer2
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.R

@Composable
fun DoctorProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // Заголовок
        Text(
            text = "Ваш профиль",
            style = H1styleVer2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Блок: ФИО
        ProfileField(title = "ФИО:", value = "Клепандина Юлия Васильевна")

        // Блок: Должность
        ProfileField(title = "Должность:", value = "Психиатр Нарколог")

        // Блок: Телефон
        ProfileField(title = "Телефон:", value = "7 (924) 456 47 36")

        // Блок: Организация
        ProfileField(title = "Медицинская организация", value = "ЦЭМП РБ№2")

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate("edit_doctor_screen")
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
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

@Composable
fun ProfileField(title: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(text = title, style = H4styleVer2)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = H4styleVer3)
    }
}
