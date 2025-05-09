package com.example.myfirstapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.screens.robotoFamily

// Set of Material typography styles to start with
val Typography = Typography(

    // H1 стили
    headlineLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF313333) // Черный
    ),

    // H1 синий вариант
    headlineMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF4B84F1) // Синий
    ),

    // H2 стиль
    headlineSmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = Color(0xFF313333) // Черный
    ),

    // H3 стили
    titleLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = Color(0xFF4B84F1) // Синий
    ),

    titleMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color(0xFFFFFFFF) // Белый (для кнопок)
    ),

    titleSmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color(0xFF4B84F1) // Синий (для кнопок)
    ),

    // H4 стили
    bodyLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color(0xFF8F9090) // Серый (для TextField)
    ),

    bodyMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Color(0xFF4B84F1) // Синий
    ),

    bodySmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Color(0xFF8F9090) // Серый
    ),

    // H5 стили
    labelLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color(0xFF8F9090) // Серый (надзаголовок)
    ),

    labelMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color(0xFF515252) // Черный (надзаголовок)
    ),

    labelSmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color(0xFF313333) // Черный (подзаголовок кнопки)
    ),

    // Дополнительные стили
    displaySmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color(0xFFFFFFFF) // Белый (для кнопок)
    ),

    displayMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Color(0xFF4B84F1) // Синий (подзаголовок)
    ),

    displayLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color(0xFF2F5EB7) // Синий текст
    )

//    default = TextStyle(
//        fontFamily = robotoFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp,
//        color = Color(0xFF555555) // Черный для лекарств
//    )




    /* Other default text styles to override
         titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)