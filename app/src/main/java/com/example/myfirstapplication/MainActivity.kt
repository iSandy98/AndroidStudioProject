package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.screens.EntryScreen
import com.example.myfirstapplication.screens.Onboarding
import com.example.myfirstapplication.screens.SignInScreen
import com.example.myfirstapplication.screens.StartScreen
import com.example.myfirstapplication.screens.TrackerScreen
import com.example.myfirstapplication.screens.robotoFamily
import com.example.myfirstapplication.ui.theme.MyFirstApplicationTheme
import com.example.myfirstapplication.classes.TimeOfDayTreatment
import com.example.myfirstapplication.screens.DrugsScreen
import com.example.myfirstapplication.screens.PatientProfile


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PatientProfile()
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






