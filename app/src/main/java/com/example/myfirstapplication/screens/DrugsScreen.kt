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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.classes.Drug
import com.example.myfirstapplication.classes.Prefs
import com.example.myfirstapplication.viewmodels.DrugsViewModel
import com.example.myfirstapplication.viewmodels.ProfileViewModel
import com.example.myfirstapplication.viewmodels.ScheduleViewModel

@Composable
fun DrugsScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    drugsViewModel: DrugsViewModel = hiltViewModel(),
    scheduleViewModel: ScheduleViewModel = hiltViewModel()
) {
    val userId = Prefs.userId
    LaunchedEffect(userId) {
        userId?.let { profileViewModel.loadProfile(it) }
    }

    val userProfile by profileViewModel.userProfile.collectAsStateWithLifecycle()
    // Когда загрузился профиль и есть телефон, загружаем лекарства
    LaunchedEffect(userProfile?.phone) {
        userProfile?.phone?.takeIf { it.isNotBlank() }?.let {
            drugsViewModel.loadDrugs(it)
        }
    }

    val drugs by drugsViewModel.drugs.collectAsStateWithLifecycle()

    LaunchedEffect(drugs) {
        if (drugs.isNotEmpty()) {
            scheduleViewModel.loadSchedule(userId ?: "")
        }
    }

    var selectedTime by remember { mutableStateOf("Утро") }

    val scheduleMap by scheduleViewModel.scheduleMap.collectAsState()

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
                    timeOfDay = "День",
                    isSelected = selectedTime == "День",
                    onClick = { selectedTime = "День" }
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
                "День" -> "Дневной прием"
                "Вечер" -> "Вечерний прием"
                "Ночь" -> "Ночной прием"
                else -> "Выберите время приема"
            },
            style = H3style,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        DrugCheckList(
            drugs = drugs,
            selectedTime = selectedTime ?: "",
            scheduleMap = scheduleMap,
            onToggle = { drugId, taken ->
                scheduleViewModel.saveEntry(userId ?: "", drugId, selectedTime!!, taken)
            }
        )
    }
}

@Composable
fun TimeOfDayButton(
    timeOfDay: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
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


@Composable
fun DrugCheckList(
    drugs: List<Drug>,
    selectedTime: String,
    scheduleMap: Map<String, Boolean>,            // пришло из viewModel
    onToggle: (drugId: String, taken: Boolean) -> Unit
) {
    val filtered = remember(drugs, selectedTime) {
        drugs.filter { it.times.contains(selectedTime) }
    }
    val takenStates = remember { mutableStateMapOf<String, Boolean>() }
    // Initialize defaults
    filtered.forEach { drug ->
        takenStates.putIfAbsent(drug.id, false)
    }

    val stateMap = remember(drugs, selectedTime, scheduleMap) {
        mutableStateMapOf<String, Boolean>().apply {
            // очистим и заполним по фильтру
            clear()
            drugs.filter { it.times.contains(selectedTime) }
                .forEach { drug ->
                    val key = "${drug.id}_$selectedTime"
                    this[key] = scheduleMap[key] ?: false
                }
        }
    }

    LazyColumn {
        items(drugs.filter { it.times.contains(selectedTime) }, key = { it.id }) { drug ->
            val key = "${drug.id}_$selectedTime"
            val taken = stateMap[key] ?: false
            DrugItem(
                drug = drug,
                isTaken = taken,
                onCheckedChange = { newValue ->
                    stateMap[key] = newValue
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = {
                // при клике сохраняем всё сразу
                stateMap.forEach { (key, taken) ->
                    val (drugId, _) = key.split("_", limit = 2)
                    onToggle(drugId, taken)
                }
            },
            modifier = Modifier.height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.blue_main),
                contentColor = colorResource(R.color.white)
            )
        ) {
            Text("Сохранить")
        }
    }

}

@Composable
fun DrugItem(
    drug: Drug,
    isTaken: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isTaken,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.blue_main),
                uncheckedColor = colorResource(R.color.gray)
            )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "${drug.name} ${drug.dose}",
            style = H3style,
            color = if (isTaken) colorResource(R.color.gray) else colorResource(R.color.black)
        )
    }
}



