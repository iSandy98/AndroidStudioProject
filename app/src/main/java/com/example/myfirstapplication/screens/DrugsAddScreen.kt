package com.example.myfirstapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.R
import com.example.myfirstapplication.classes.Drug
import com.example.myfirstapplication.viewmodels.DrugsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugsAddScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DrugsViewModel = hiltViewModel(),
    onFinish: () -> Unit,
    phone:String
) {

    LaunchedEffect(phone) {
        if (phone.isNotBlank()) {
            viewModel.loadDrugs(phone)
        }
    }

    var showBottom by remember { mutableStateOf(false) }
    var selectedDrug by remember { mutableStateOf<Drug?>(null) }
    val drugs by viewModel.drugs.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    Column(modifier = modifier.fillMaxWidth()) {
        /*Text(
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
        Spacer(Modifier.size(30.dp))*/
        /*Row(
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
        Spacer(modifier = Modifier.size(30.dp))*/
        Text(
            text = "Текущее лечение",
            style = H3style,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        if (drugs.isNotEmpty()) {
            Column {
                drugs.forEach { drug ->
                    DrugListItem(drug = drug, onEditClick = {
                        selectedDrug = drug
                        showBottom = true
                    })
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
                    fontSize = 12.sp,
                    color = colorResource(R.color.gray)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    selectedDrug = null
                    showBottom = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить лекарство"
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
            ){
            Button(
                modifier = Modifier.height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue_main),
                    contentColor = colorResource(R.color.white)
                ),
                onClick = {
                    onFinish()
                }
            ) {
                Text("Сохранить")
            }
        }

        if (showBottom) {
            ModalBottomSheet(
                onDismissRequest = { showBottom = false },
                sheetState = sheetState
            ) {
                EditDrugSheet(
                    drug = selectedDrug ?: Drug(id = "", name = "", dose = "", times = emptyList()),
                    onSave = { name, dose, times ->
                        if (selectedDrug == null) {
                            // add new
                            viewModel.addDrug(phone, Drug(id = "", name = name, dose = dose, times = times)) { success ->
                                if (success) showBottom = false
                            }
                        } else {
                            // update existing
                            val updated = selectedDrug!!.copy(name = name, dose = dose, times = times)
                            viewModel.updateDrug(phone, updated) { success ->
                                if (success) showBottom = false
                            }
                        }
                    },
                    onDelete = if (selectedDrug != null) {
                        {
                            viewModel.deleteDrug(phone, selectedDrug!!.id) { success ->
                                if (success) showBottom = false
                            }
                        }
                    } else null
                )
            }
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

/**
 * Компонент редактирования/добавления лекарства в виде модального BottomSheet.
 *
 * @param drug начальные данные лекарства (при добавлении можно передать пустой Drug("", "", emptyList()))
 * @param onSave вызывается при нажатии "Сохранить"
 * @param onDelete вызывается при нажатии "Удалить" (если нужно)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDrugSheet(
    drug: Drug,
    onSave: (name: String, dose: String, times: List<String>) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var name by remember { mutableStateOf(drug.name) }
    var dose by remember { mutableStateOf(drug.dose) }
    var morning by remember { mutableStateOf("Утро" in drug.times) }
    var day by remember { mutableStateOf("День" in drug.times) }
    var evening by remember { mutableStateOf("Вечер" in drug.times) }
    var night by remember { mutableStateOf("Ночь" in drug.times) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Заголовок
        Text(
            text = if (drug.name.isNotEmpty()) "Редактирование лекарства" else "Добавить лекарство",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // Поле ввода "Название лекарства"
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название лекарства") },
            shape = RoundedCornerShape(24.dp),        // скруглённые углы
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)                         // высота, как на макете
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Поле ввода "Дозировка"
        OutlinedTextField(
            value = dose,
            onValueChange = { dose = it },
            label = { Text("Дозировка") },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Заголовок для выбора времени приёма
        Text(
            text = "Время приёма",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Ряды с чекбоксами
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Утро
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = morning,
                    onCheckedChange = { morning = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.green_checked),
                        uncheckedColor = Color.LightGray
                    ),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(4.dp))
                Text(text = "Утро", fontSize = 14.sp)
            }
            // День
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = day,
                    onCheckedChange = { day = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.green_checked),
                        uncheckedColor = Color.LightGray
                    ),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(4.dp))
                Text(text = "День", fontSize = 14.sp)
            }
            // Вечер
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = evening,
                    onCheckedChange = { evening = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.green_checked),
                        uncheckedColor = Color.LightGray
                    ),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(4.dp))
                Text(text = "Вечер", fontSize = 14.sp)
            }
            // Ночь
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = night,
                    onCheckedChange = { night = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.green_checked),
                        uncheckedColor = Color.LightGray
                    ),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(4.dp))
                Text(text = "Ночь", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопки "Сохранить" и "Удалить"
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Кнопка Сохранить
            Button(
                onClick = {
                    // Собираем выбранные времена
                    val times = buildList {
                        if (morning) add("Утро")
                        if (day) add("День")
                        if (evening) add("Вечер")
                        if (night) add("Ночь")
                    }
                    onSave(name, dose, times)
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue_main),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Text(text = "Сохранить")
            }

            // Кнопка Удалить (опционально)
            if (onDelete != null) {
                TextButton(
                    onClick = {
                        onDelete()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(colorResource(R.color.red_delete)),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colorResource(R.color.white)
                    )
                ) {
                    Text(text = "Удалить")
                }
            }
        }
    }
}