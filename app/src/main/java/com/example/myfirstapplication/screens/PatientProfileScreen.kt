package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.H5style

@Preview(showBackground = true)
@Composable
fun PatientProfile() {
    Column(
        modifier = Modifier.fillMaxWidth(),

    ) {
        Text("ФИО:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("Иванов Максим Иванович", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Дата рождения:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("02.04.1987", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Телефон:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("7 (924) 856 45 36", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Адрес:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("г.Якутск, ул. Ленина 1", style = H4styleVer3)
        Spacer(Modifier.size(30.dp))
        Text("Медицинская организация", style = H3style)
        Spacer(Modifier.size(23.dp))
        Text("Лечащий врач", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("Иванова А.П.", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Диагноз", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("F32.1(Депрессия)", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Группа крови", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text("A(II) Rh+", style = H4styleVer3)
    }

}