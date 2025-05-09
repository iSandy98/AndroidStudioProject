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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.R
import com.example.myfirstapplication.classes.Drug
import com.example.myfirstapplication.database

@Preview (showBackground = true)
@Composable
fun PatientProfileScreen(){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ){
        item {
            PatientMainData()
        }
        item{
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                ) {
                Text(text = "Медицинская информация", style = H2style)
            }
        }
        item {
            CurrentTreatment(database.drugs)
        }
    }
}

@Composable
fun PatientMainData(){
    Text("ФИО:", style = H2style)
    Spacer(Modifier.size(4.dp))
    Text("Иванов Максим Иванович", style = H2style)
    Spacer(Modifier.size(4.dp))
    Text("Дата рождения:", style = H2style)
    Spacer(Modifier.size(4.dp))
    Text("02.04.1987", style = H2style)
    Spacer(Modifier.size(4.dp))
    Text("Телефон:", style = H2style)
    Spacer(Modifier.size(4.dp))
    Text("+7 (924) 456 87 67", style = H2style)
    Spacer(Modifier.size(4.dp))
    Text("Адрес:", style = H2style)
    Spacer(Modifier.size(4.dp))
    Text("г. Якутск, ул. Ленина 1", style = H2style)
    Spacer(Modifier.size(4.dp))
}

@Composable
fun CurrentTreatment(drugs: List<Drug>){

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Текущее лечение:", style = H2style)
        Row {
            Column(modifier = Modifier.weight(1f)) {
                TimeOfDayElement("Утро:", drugs)
                TimeOfDayElement("Вечер:", drugs)
            }
            Column(modifier = Modifier.weight(1f)) {
                TimeOfDayElement("День:", drugs)
                TimeOfDayElement("Ночь:", drugs)
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.height(48.dp),
            colors = ButtonColors(
                containerColor = colorResource(R.color.blue_main),
                contentColor = colorResource(R.color.white),
                disabledContainerColor = colorResource(R.color.blue_disable),
                disabledContentColor = colorResource(R.color.gray)
            )
        ) {
            Text(text = "Изменить лечение")
        }
    }
}

@Composable
fun TimeOfDayElement(timeOfDay: String, drugs: List<Drug>){
    Column {
        Text(text = timeOfDay, style = H2style)
        Spacer(Modifier.size(4.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(100.dp)
        ) {
            items(drugs) {drug -> DrugItem(drug)}
        }
    }

}


@Composable
fun DrugItem(drug: Drug) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${drug.name} (${drug.dosage})", style = H2style)
    }
}