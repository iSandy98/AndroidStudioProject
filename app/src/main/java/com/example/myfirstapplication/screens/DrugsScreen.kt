package com.example.myfirstapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.classes.Drug


@Preview(showBackground = true)
@Composable
fun DrugsScreen() {
    var selectedTime by remember { mutableStateOf<String?>("Утро") }

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
        DrugCheckList(selectedTime)
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


@Composable
fun DrugCheckList(selectedTime: String?) {
    val drugs = remember {
        listOf(
            Drug("Аспирин", "100мг", listOf("Утро", "Вечер")),
            Drug("Витамины","100мг", listOf("Утро")),
            Drug("Омепразол","100мг", listOf("Обед")),
            Drug("Мелатонин","100мг", listOf("Ночь"))
        )
    }

    val filtereddrugs = drugs.filter {
        selectedTime == null || it.times.contains(selectedTime)
    }

    LazyColumn {

        items(filtereddrugs) { drug ->
            var isTaken by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isTaken,
                    onCheckedChange = {isTaken = it},
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.blue_main),
                        uncheckedColor = colorResource(R.color.gray)
                    )
                )
                Spacer(Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = drug.name,
                        style = H3style,
                        color = if (isTaken) colorResource(R.color.gray) else colorResource(R.color.blue_main)
                    )

                    Text(
                        text = drug.times.joinToString(", "),
                        style = H5style,
                        color = colorResource(R.color.gray)
                    )
                }
            }
        }
    }
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
            Text(text = "Сохранить")
        }
    }

}

@Composable
fun DrugItem(
    drug: Drug,
    modifier: Modifier = Modifier
) {
    var isTaken by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isTaken,
            onCheckedChange = { isTaken = it },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.blue_main),
                uncheckedColor = colorResource(R.color.gray)
            )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = drug.name,
                style = H3style,
                color = if (isTaken) colorResource(R.color.gray) else colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = drug.times.joinToString(", "),
                style = H5style,
                color = colorResource(R.color.gray)
            )
        }
    }
}



