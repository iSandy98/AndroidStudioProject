package com.example.myfirstapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.R
import com.example.myfirstapplication.classes.Drug
import com.example.myfirstapplication.classes.DrugStatus
import com.example.myfirstapplication.classes.Mood
import com.example.myfirstapplication.whoVisit
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale




@Composable
fun PatientProfileScreen(navController: NavHostController) {
    if (whoVisit == "Пациент"){
        PatientPerspective()
    }else{
        DoctorPerspective()
    }
}

@Composable
fun DoctorPerspective() {
    val moodData = remember {
        listOf(
            Mood("Пн", 1),
            Mood("Вт", 2),
            Mood("Ср", 3),
            Mood("Чт", 4),
            Mood("Пт", 5),
            Mood("Сб", 6),
            Mood("Вс", 7)
        )
    }
    val sleepData = remember {
        listOf(
            Mood("Пн", 7),
            Mood("Вт", 6),
            Mood("Ср", 5),
            Mood("Чт", 4),
            Mood("Пт", 3),
            Mood("Сб", 2),
            Mood("Вс", 1)
        )
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item{
            DrugSchedule()
        }
        item{
            DrugCalendar()
        }
        item{
            Diagramma(moodData, "Диаграмма настроения")
        }
        item{
            Diagramma(sleepData, "Диаграмма Cна")
        }

    }
}

@Composable
fun DrugCalendar() {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    //Пример
    val calendarData = remember(currentDate) {
        val daysInMonth = currentDate.lengthOfMonth()
        List(daysInMonth) { day ->
            when ((day + 1) % 5) {
                0 -> DrugStatus.MISSED
                1 -> DrugStatus.PARTIAL
                else -> DrugStatus.TAKEN
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Панель навигации
        MonthNavigation(
            currentDate = currentDate,
            onPrevMonth = { currentDate = currentDate.minusMonths(1) },
            onNextMonth = { currentDate = currentDate.plusMonths(1) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        WeekDaysHeader()

        Spacer(modifier = Modifier.height(8.dp))

        CalendarGrid(currentDate = currentDate, calendarData = calendarData)
    }
}

@Composable
fun CalendarGrid(
    currentDate: LocalDate,
    calendarData: List<DrugStatus>
) {
    val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek.value // 1=Пн, 7=Вс
    val daysInMonth = currentDate.lengthOfMonth()
    val today = LocalDate.now()

    val emptyCells = (firstDayOfWeek - 1) % 7
    var dayCounter = 1

    Column(modifier = Modifier.fillMaxWidth()) {
        repeat(6) { week -> // Максимум 6 недель в месяце
            if (dayCounter <= daysInMonth) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(7) { column ->
                        if (week == 0 && column < emptyCells) {
                            // Пустые ячейки в первой строке
                            Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                        } else if (dayCounter <= daysInMonth) {
                            // Ячейка с числом
                            CalendarDay(
                                day = dayCounter,
                                status = calendarData[dayCounter - 1],
                                isToday = currentDate.withDayOfMonth(dayCounter) == today,
                                modifier = Modifier.weight(1f)
                            )
                            dayCounter++
                        } else {
                            // Пустые ячейки после последнего числа
                            Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeekDaysHeader() {
    val weekDays = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        weekDays.forEach { day ->
            Text(
                text = day,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MonthNavigation(
    currentDate: LocalDate,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPrevMonth) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous month")
        }

        Text(
            text = currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentDate.year,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = onNextMonth) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next month")
        }
    }
}

@Composable
private fun CalendarDay(
    day: Int,
    status: DrugStatus,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val (bgColor, borderColor) = when (status) {
        DrugStatus.TAKEN -> colors.primaryContainer to colors.primary
        DrugStatus.PARTIAL -> colors.secondaryContainer to colors.secondary
        DrugStatus.MISSED -> colors.errorContainer to colors.error
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(bgColor, CircleShape)
            .border(
                width = if (isToday) 2.dp else 1.dp,
                color = if (isToday) colors.primary else borderColor,
                shape = CircleShape
            )
            .clickable { /* Обработка клика */ },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$day",
            color = if (isToday) colors.primary else colors.onSurface,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun Diagramma(moodData: List<Mood>, header: String) {
    // Пример данных за последние 7 дней

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = header,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Основной контейнер диаграммы
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            // Столбцы диаграммы
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                moodData.forEach { data ->
                    MoodBar(day = data.day, value = data.value)
                }
            }
        }
    }
}

@Composable
private fun MoodBar(day: String, value: Int) {
    val color = when (value) {
        in 0..3 -> Color.Red
        in 4..6 -> Color.Yellow
        else -> Color.Green
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(40.dp)
    ) {
        // Столбец диаграммы
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((value * 25).dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                )
                .padding(horizontal = 8.dp)
        )

        // Подпись дня
        Text(
            text = day,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun DrugSchedule(){
    val drugs = remember {
        listOf(
            Drug("Аспирин","100мг", listOf("Утро", "Вечер")),
            Drug("Витамины", "100мг", listOf("Утро")),
            Drug("Омепразол", "100мг", listOf("Обед")),
            Drug("Мелатонин", "100мг", listOf("Ночь"))
        )
    }
    val drugsByTime = remember(drugs) {
        mapOf(
            "Утро" to drugs.filter { it.times.contains("Утро") },
            "Обед" to drugs.filter { it.times.contains("Обед") },
            "Вечер" to drugs.filter { it.times.contains("Вечер") },
            "Ночь" to drugs.filter { it.times.contains("Ночь") }
        )
    }
    val maxItemsInColumn = remember(drugsByTime) {
        drugsByTime.values.maxOfOrNull { it.size } ?: 0
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "График приема лекарств",
            style = H2style.copy(fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.blue_main)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Левый столбец (Утро и Вечер)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TimeBlock(
                    time = "Утро",
                    title = "Утренний прием",
                    drugs = drugsByTime["Утро"] ?: emptyList(),
                    minHeight = maxItemsInColumn
                )

                TimeBlock(
                    time = "Вечер",
                    title = "Вечерний прием",
                    drugs = drugsByTime["Вечер"] ?: emptyList(),
                    minHeight = maxItemsInColumn
                )
            }

            // Правый столбец (Обед и Ночь)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TimeBlock(
                    time = "Обед",
                    title = "Дневной прием",
                    drugs = drugsByTime["Обед"] ?: emptyList(),
                    minHeight = maxItemsInColumn
                )

                TimeBlock(
                    time = "Ночь",
                    title = "Ночной прием",
                    drugs = drugsByTime["Ночь"] ?: emptyList(),
                    minHeight = maxItemsInColumn
                )
            }
        }
    }
}


@Composable
private fun TimeBlock(
    time: String,
    title: String,
    drugs: List<Drug>,
    minHeight: Int
) {
    // Вычисляем высоту блока на основе максимального количества элементов
    val itemHeight = 48.dp
    val headerHeight = 56.dp
    val minBlockHeight = headerHeight + itemHeight * minHeight

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minBlockHeight),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        border = BorderStroke(1.dp, colorResource(R.color.blue_main).copy(alpha = 0.3f)),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Заголовок блока
            Text(
                text = title,
                style = H3style.copy(fontSize = 16.sp),
                color = colorResource(R.color.blue_main),
                modifier = Modifier.padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            HorizontalDivider(
                color = colorResource(R.color.blue_main).copy(alpha = 0.3f),
                thickness = 1.dp
            )

            // Список лекарств или заглушка, если лекарств нет
            if (drugs.isNotEmpty()) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    drugs.forEach { drug ->
                        DrugScheduleItem(drug = drug)
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
        }
    }
}

@Composable
fun DrugScheduleItem(drug: Drug) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = drug.name
        )
        Text(
            text = drug.dose
        )
    }
}


@Composable
fun PatientPerspective(){
    Column(
        modifier = Modifier.fillMaxWidth(),

        ){
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