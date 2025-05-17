package com.example.myfirstapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.myfirstapplication.screens.StartScreen
import com.example.myfirstapplication.screens.robotoFamily
import com.example.myfirstapplication.screens.PatientProfileScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myfirstapplication.classes.Prefs
import com.example.myfirstapplication.screens.DoctorAppointmentScreen
import com.example.myfirstapplication.screens.DoctorMainMenuScreen
import com.example.myfirstapplication.screens.DoctorProfileScreen
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
import com.example.myfirstapplication.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

var whoVisit: String = ""

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            MyApp()
                //DrugsAddScreen(navController)
            //SignUpScreen(navController)
                //DoctorMainMenuScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    onNavigationClick: () -> Unit
) {
    val showBackButton = title.isNotBlank() && title != "Онбординг" && title != "Начало" && title != "Регистрация"

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF4B84F1),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
fun MyBottomBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val items = if (whoVisit == "Пациент") {
        listOf(
            BottomNavItem("tracker_screen", Icons.Default.Home, "Трекер"),
            BottomNavItem("drugs_screen", Icons.Default.CheckCircle, "Лекарства"),
            BottomNavItem("patient_profile_screen", Icons.Default.Person, "Профиль")
        )
    } else {
        listOf(
            BottomNavItem("notification_screen", Icons.Default.Notifications, "Почта"),
            BottomNavItem("edit_doctor_screen", Icons.Default.Edit, "Настройки"),
            BottomNavItem("doctor_main_menu_screen", Icons.Default.Face, "Список"),
            BottomNavItem("doctor_profile_screen", Icons.Default.Person, "Профиль")
        )
    }

    val accentColor = Color.Blue// синий акцент
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState().value

        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),
                            tint = if (selected) accentColor else Color.Blue
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selected) accentColor else Color.Gray
                        )
                    }
                },
                alwaysShowLabel = false // убирает стандартный label, мы его рисуем вручную
            )
        }
    }

}

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route

    val screenTitles = mapOf(
        "tracker_screen" to "",
        "sign_up_screen" to "",
        "sign_in_screen" to "Вход",
        "patient_profile_screen" to "",
        "notification_screen" to "Уведомления",
        "entry_screen" to "Вход",
        "edit_patient_screen" to "",
        "edit_doctor_screen" to "",
        "drugs_screen" to "Лекарства",
        "drugs_add_screen" to "Добавить лекарство",
        "doctor_profile_screen" to "",
        "doctor_main_menu_screen" to "",
        "doctor_appointment_screen" to "Приемы"
    )

    val showBottomBar = currentRoute in listOf(
        "tracker_screen",
        "drugs_screen",
        "patient_profile_screen",
        "doctor_main_menu_screen",
        "notification_screen",
        "edit_doctor_screen",
        "doctor_profile_screen"
    )

    val title = screenTitles[currentRoute] ?: ""

    Scaffold(
        topBar = {
            MyTopBar(
                title = title,
                onNavigationClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                MyBottomBar(navController, currentRoute)
            }
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ){
            NavHost(
                navController = navController,
                startDestination = "onboarding_screen",
                route = "root_graph"
            ) {
                composable("start_screen") { StartScreen(navController) }
                composable("tracker_screen") { TrackerScreen(navController) }
                composable("sign_up_screen") { SignUpScreen(navController) }
                composable("sign_in_screen") { SignInScreen(navController) }
                composable("patient_profile_screen/{phone}") { backStackEntry ->
                    val phone = backStackEntry.arguments?.getString("phone") ?: ""
                    PatientProfileScreen(navController, phone = phone)
                }
                composable("patient_profile_screen") {
                    PatientProfileScreen(navController, phone = "")
                }

                composable("onboarding_screen") { Onboarding(navController) }
                composable("notification_screen") { Notifications(navController) }
                composable("entry_screen") { EntryScreen(navController) }
                composable(
                    "edit_patient_screen/{phone}",
                ) { backStackEntry ->
                    val phone = backStackEntry.arguments?.getString("phone") ?: ""
                    EditPatientProfile(navController, phone = phone)
                }
                composable("edit_doctor_screen") { EditDoctorProfile(navController) }
                composable("drugs_screen") { DrugsScreen(navController) }
                composable("drugs_add_screen") { DrugsAddScreen(navController, Modifier, onFinish = {}, phone = "") }
                composable("doctor_profile_screen") { DoctorProfileScreen(navController) }
                composable("doctor_main_menu_screen") { DoctorMainMenuScreen(navController) }
                composable("doctor_appointment_screen") { DoctorAppointmentScreen(navController) }
            }
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