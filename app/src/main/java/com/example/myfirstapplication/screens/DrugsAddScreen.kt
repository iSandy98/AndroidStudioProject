package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.R
import com.example.myfirstapplication.classes.Drug


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugsAddScreen(navController: NavHostController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedDrug by remember { mutableStateOf<Drug?>(null) }

    val drugs = remember {
        listOf(
            Drug("Аспирин","100мг", listOf("Утро", "Вечер")),
            Drug("Витамины", "100мг", listOf("Утро")),
            Drug("Омепразол", "100мг", listOf("Обед")),
            Drug("Мелатонин", "100мг", listOf("Ночь"))
        )
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Редактирование профиля пациента",
            style = H3style,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(30.dp))
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
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            text = "Текущее лечение",
            style = H3style,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        if (drugs.isNotEmpty()) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                drugs.forEach { drug ->
                    DrugListItem(
                        drug = drug,
                        onEditClick = {
                            selectedDrug = drug
                            showBottomSheet = true
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Нет назначений",
                    style = H5style.copy(fontSize = 12.sp),
                    color = colorResource(R.color.gray)
                )
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = rememberModalBottomSheetState()
            ) {
                // Здесь форма редактирования лекарства
                selectedDrug?.let { drug ->
                    EditDrugSheet(drug = drug) {
                        // Здесь обработка сохранения изменений
                        showBottomSheet = false
                    }
                }
            }
        }
        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Редактировать"
            )
        }
        Button(onClick = {}) {
            Text("Сохранить")
        }
    }
}

@Composable
fun DrugListItem(drug: Drug, onEditClick: () -> Unit ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = drug.name)
        Text(text = drug.times.size.toString() + " р/д")
        Text(text = drug.dose)
        IconButton(
            onClick = onEditClick
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Редактировать"
            )
        }
    }
}

@Composable
fun EditDrugSheet(
    drug: Drug,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Редактирование ${drug.name}",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Поля для редактирования
        var name by remember { mutableStateOf(drug.name) }
        var dose by remember { mutableStateOf(drug.dose) }
        var times by remember { mutableStateOf(drug.times.joinToString(", ")) }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dose,
            onValueChange = { dose = it },
            label = { Text("Дозировка") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = times,
            onValueChange = { times = it },
            label = { Text("Время приема (через запятую)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }
    }
}



