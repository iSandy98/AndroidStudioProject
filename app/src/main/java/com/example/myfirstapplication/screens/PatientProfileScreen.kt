package com.example.myfirstapplication.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H1styleVer2
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.R
import com.example.myfirstapplication.classes.Drug
import com.example.myfirstapplication.classes.DrugStatus
import com.example.myfirstapplication.classes.Mood
import com.example.myfirstapplication.classes.Prefs
import com.example.myfirstapplication.viewmodels.DrugCalendarViewModel
import com.example.myfirstapplication.viewmodels.DrugsViewModel
import com.example.myfirstapplication.viewmodels.ProfileViewModel
import com.example.myfirstapplication.viewmodels.WellbeingViewModel
import com.example.myfirstapplication.whoVisit
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun PatientProfileScreen(
    navController: NavHostController,
    phone: String,
) {
    if (whoVisit == "Пациент") {
        PatientPerspective(navController = navController)
    } else {
        DoctorPerspective(
            navController = navController,
            phone = phone
        )
    }
}


@Composable
fun DoctorPerspective(
    navController: NavHostController,
    phone: String,
    wellbeingViewModel: WellbeingViewModel = hiltViewModel()
) {
    LaunchedEffect(phone) {
        wellbeingViewModel.loadHistoryForPhone(phone)
    }

    val moodData  by wellbeingViewModel.moodHistory.collectAsState()
    val sleepData by wellbeingViewModel.sleepHistory.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            DrugSchedule(
                navController = navController,
                phone = phone
            )
        }
        item {
            DrugCalendar(phone = phone)
        }
        item {
            Diagramma(moodData, "Диаграмма настроения")
        }
        item {
            Diagramma(sleepData, "Диаграмма Cна")
        }

    }
}

@Composable
fun DrugCalendar(
    viewModel: DrugCalendarViewModel = hiltViewModel(),
    phone:String
) {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    // При смене месяца загружаем данные
    LaunchedEffect(currentDate) {
        viewModel.loadMonth(YearMonth.from(currentDate), phone)
    }

    val statuses by viewModel.statuses.collectAsState()
    val daysInMonth = currentDate.lengthOfMonth()

    val calendarData = List(daysInMonth) { idx ->
        val date = currentDate.withDayOfMonth(idx + 1)
        statuses[date] ?: DrugStatus.MISSED
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

        CalendarGrid(
            currentDate = currentDate,
            calendarData = calendarData
        )
    }
}

@Composable
fun CalendarGrid(
    currentDate: LocalDate,
    calendarData: List<DrugStatus>
) {
    val firstDayOffset = (currentDate.withDayOfMonth(1).dayOfWeek.value + 6) % 7
    val totalDays = calendarData.size
    val today = LocalDate.now()
    var dayCounter = 1

    Column(Modifier.fillMaxWidth()) {
        repeat(6) { week ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(7) { col ->
                    if ((week == 0 && col < firstDayOffset) || dayCounter > totalDays) {
                        // пустая ячейка
                        Box(
                            Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    } else {
                        CalendarDay(
                            day = dayCounter,
                            status = calendarData[dayCounter - 1],
                            isToday = currentDate.withDayOfMonth(dayCounter) == today,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                        )
                        dayCounter++
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
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                color = Color.Blue
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
            text = currentDate.month.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ) + " " + currentDate.year,
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

    Log.d("CalendarDay", status.toString())

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(bgColor, CircleShape)
            .border(
                width = if (isToday) 2.dp else 1.dp,
                color = if (isToday) colors.primary else borderColor,
                shape = CircleShape
            ),
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
            modifier = Modifier.padding(bottom = 16.dp),
            style = H1styleVer2
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
fun DrugSchedule(
    navController: NavHostController,
    phone: String,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    drugsViewModel: DrugsViewModel = hiltViewModel()
) {
    LaunchedEffect(phone) {
        if (phone.isNotBlank()) {
            profileViewModel.loadProfileByPhone(phone)
            drugsViewModel.loadDrugs(phone)
        }
    }

    val profile by profileViewModel.userProfile.collectAsState()
    val drugs by drugsViewModel.drugs.collectAsState()

    if (profile == null) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    var showChangeTreatment by remember { mutableStateOf(false) }

    val drugsByTime = remember(drugs) {
        listOf("Утро", "День", "Вечер", "Ночь").associateWith { time ->
            drugs.filter { it.times.contains(time) }
        }
    }

    val maxItemsInColumn = remember(drugsByTime) {
        drugsByTime.values.maxOfOrNull { it.size } ?: 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("ФИО:", style = H4styleVer2)
        Spacer(Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Сам текст ФИО
            Text(
                text = profile?.fullName ?: "",
                style = H4styleVer3,
            )

            // Небольшой отступ перед иконкой
            Spacer(Modifier.width(40.dp))

            // Иконка редактирования — размер 24dp, чтобы «сидела» по высоте строки
            IconButton(
                onClick = {
                    val phoneArg = profile?.phone ?: ""
                    navController.navigate("edit_patient_screen/$phoneArg")
                },
                modifier = Modifier
                    .size(24.dp) // убираем стандартный 48dp hit-box, чтобы не «выпирал»
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Редактировать ФИО",
                    modifier = Modifier.size(24.dp)  // чтобы иконка тоже была 24dp
                )
            }
        }

        Spacer(Modifier.size(10.dp))
        Text("Дата рождения:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text(profile?.birthDate ?: "", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Телефон:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text(profile?.phone ?: "", style = H4styleVer3)
        Spacer(Modifier.size(15.dp))
        Text("Адрес:", style = H4styleVer2)
        Spacer(Modifier.size(5.dp))
        Text(profile?.address ?: "", style = H4styleVer3)
        Spacer(Modifier.size(30.dp))

        if(!showChangeTreatment) {
            Text(
                text = "График приема лекарств",
                style = H1styleVer2.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.blue_main)
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
        if (!showChangeTreatment) {
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
                        time = "День",
                        title = "Дневной прием",
                        drugs = drugsByTime["День"] ?: emptyList(),
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
        if (!showChangeTreatment) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.blue_main),
                        contentColor = colorResource(R.color.white)
                    ),
                    onClick = {
                        showChangeTreatment = !showChangeTreatment
                    }
                ) {
                    Text("Изменить лечение")
                }
            }
        }
        if (showChangeTreatment) {
            // экран добавления / редактирования лекарств
            DrugsAddScreen(
                navController = navController,
                modifier = Modifier.fillMaxWidth(),
                onFinish = { showChangeTreatment = false },
                phone = phone
            )
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
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Заголовок блока
            Text(
                text = title,
                style = H3style.copy(fontSize = 16.sp),
                color = colorResource(R.color.blue_main),
                modifier = Modifier
                    .padding(bottom = 8.dp)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = drug.name
        )
        Text(
            text = drug.dose
        )
    }
}


@Composable
fun PatientPerspective(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val userId = remember { Prefs.userId ?: "" }
    Log.d("PROFILE", "LaunchedEffect got userId = '$userId'")

    LaunchedEffect(userId) {
        if (userId.isNotBlank()) {
            viewModel.loadProfile(userId)
        }
    }

    // получаем профиль
    val profile by viewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        if (profile == null) {
            // показываем загрузку
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Column
        }

        Text(
            text = "Ваш профиль",
            textAlign = TextAlign.Center,
            style = H1styleVer2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        profile?.fullName?.let { PatientField("ФИО:", it) }
        profile?.birthDate?.let { PatientField("Дата рождения:", it) }
        profile?.phone?.let { PatientField("Телефон:", it) }
        profile?.address?.let { PatientField("Адрес:", it) }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Медицинская организация",
            textAlign = TextAlign.Center,
            style = H1styleVer2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        profile?.doctor?.let { PatientField("Лечащий врач:", it) }
        profile?.diagnosis?.let { PatientField("Диагноз:", it) }
        profile?.bloodGroup?.let { PatientField("Группа крови:", it) }

        Button(
            onClick = {
                // очистить сохранённый ID
                Prefs.userId = null
                // навигация на экран onboarding, очищая стек
                navController.navigate("onboarding_screen") {
                    popUpTo("onboarding_screen") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(text = "Выйти из профиля")
        }
    }
}


@Composable
fun PatientField(title: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = title, style = H4styleVer2)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = H4styleVer3)
    }
}
