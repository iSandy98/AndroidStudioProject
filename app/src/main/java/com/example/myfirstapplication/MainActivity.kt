package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.screens.StartScreen
import com.example.myfirstapplication.screens.robotoFamily
import com.example.myfirstapplication.screens.PatientProfileScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myfirstapplication.screens.DoctorAppointmentScreen
import com.example.myfirstapplication.screens.DoctorMainMenuScreen
import com.example.myfirstapplication.screens.DoctorProfileScreen
import com.example.myfirstapplication.screens.DrugTracker
import com.example.myfirstapplication.screens.DrugsAddScreen
import com.example.myfirstapplication.screens.DrugsScreen
import com.example.myfirstapplication.screens.EditDoctorProfile
import com.example.myfirstapplication.screens.EditPatientProfile
import com.example.myfirstapplication.screens.EntryScreen
import com.example.myfirstapplication.screens.Notifications
import com.example.myfirstapplication.screens.Onboarding
import com.example.myfirstapplication.screens.SignInScreen
import com.example.myfirstapplication.screens.SignUpScreen
import com.example.myfirstapplication.screens.TrackerScreen

var whoVisit: String = ""

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyTopBar(
    title: String,
    onNavigationClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFD0EBFF))
    ) {
        IconButton(
            onClick = onNavigationClick,
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.CenterStart)
                .padding(start = 16.dp, top = 10.dp, bottom = 11.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад",
                tint = Color.Black
            )
        }

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(LineHeightStyle.Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val screenTitles = mapOf(
        "start_screen" to "Начало",
        "tracker_screen" to "Трекер",
        "sign_up_screen" to "Регистрация",
        "sign_in_screen" to "Вход",
        "patient_profile_screen" to "Профиль пациента",
        "onboarding_screen" to "Онбординг",
        "notification_screen" to "Уведомления",
        "entry_screen" to "Вход",
        "edit_patient_screen" to "Редактировать пациента",
        "edit_doctor_screen" to "Редактировать врача",
        "drugs_screen" to "Лекарства",
        "drugs_add_screen" to "Добавить лекарство",
        "doctor_profile_screen" to "Профиль врача",
        "doctor_main_menu_screen" to "Меню врача",
        "doctor_appointment_screen" to "Приемы"
    )

    val title = screenTitles[currentRoute] ?: ""

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MyTopBar(
            title = title,
            onNavigationClick = { navController.popBackStack() }
        )

        NavHost(
            navController = navController,
            startDestination = "onboarding_screen"
        ) {
            composable("start_screen") { StartScreen(navController) }
            composable("tracker_screen") { TrackerScreen(navController) }
            composable("sign_up_screen") { SignUpScreen(navController) }
            composable("sign_in_screen") { SignInScreen(navController) }
            composable("patient_profile_screen") { PatientProfileScreen(navController) }
            composable("onboarding_screen") { Onboarding(navController) }
            composable("notification_screen") { Notifications(navController) }
            composable("entry_screen") { EntryScreen(navController) }
            composable("edit_patient_screen") { EditPatientProfile(navController) }
            composable("edit_doctor_screen") { EditDoctorProfile(navController) }
            composable("drugs_screen") { DrugsScreen(navController) }
            composable("drugs_add_screen") { DrugsAddScreen(navController) }
            composable("doctor_profile_screen") { DoctorProfileScreen(navController) }
            composable("doctor_main_menu_screen") { DoctorMainMenuScreen(navController) }
            composable("doctor_appointment_screen") { DoctorAppointmentScreen(navController) }
        }

        BottomAppBar {
            // тут будет bottom bar
        }
    }
}

//H1
//Главный заголовок черный
val H1style = TextStyle(fontSize = 20.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Bold, color = Color(0xFF313333))

//Главный заголовок синий
val H1styleVer2 = TextStyle(fontSize = 20.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Bold, color = Color(0xFF4B84F1))

//H2
val H2style = TextStyle(fontSize = 18.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium, color = Color(0xFF313333))

//H3
//Для заголовков синих
val H3style = TextStyle(fontSize = 16.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium, color = Color(0xFF4B84F1))

//H3 - для кнопки с белым текстом жирным
val H3styleVer2 = TextStyle(fontSize = 16.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Bold, color = Color(0xFFFFFFFF))

//H3 - для кнопки с синим жирным текстом
val H3styleVer3 = TextStyle(fontSize = 16.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Bold, color = Color(0xFF4B84F1))

//H4
//Для текста TextField
val H4style = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Normal, color = Color(0xFF8F9090))

//Для синего заголовка
val H4styleVer2 = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium, color = Color(0xFF4B84F1))

//Для серого текста
val H4styleVer3 = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium, color = Color(0xFF8F9090))

//H5
//Надзаголовок серый
val H5style = TextStyle(fontSize = 12.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Normal, color = Color(0xFF8F9090))

//Надзаголовок черный
val H5styleBlack = TextStyle(fontSize = 12.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Normal, color = Color(0xFF515252))

//Подзаголовок кнопки Черный
val H5styleVer2 = TextStyle(fontSize = 12.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Normal, color = Color(0xFF313333))

//Подзаголовок кнопки - Белый, жирный и для самой кнопки синей
val H5styleVer3 = TextStyle(fontSize = 12.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Bold, color = Color(0xFFFFFFFF))

//Подзаголовок кнопки Синий и текстов
val H5styleVer4 = TextStyle(fontSize = 12.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium, color = Color(0xFF4B84F1))

//Синий текст
val H5styleVer5 = TextStyle(fontSize = 12.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Normal, color = Color(0xFF2F5EB7))

//Черный текст для лекарств
val H5styleVer6 = TextStyle(fontSize = 12.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Normal, color = Color(0xFF555555))






