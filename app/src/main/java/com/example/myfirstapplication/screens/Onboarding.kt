package com.example.myfirstapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController


@Composable
fun Onboarding(navController: NavHostController) {
    // Данные для экранов онбординга
    val onboardingScreens = listOf(
        OnboardingScreen(
            title = "Добро пожаловать в Moody!",
            description = "Ваш помощник для контроля настроения, сна и связи с врачом. Просто и безопасно",
            imageRes = R.drawable.first_photo
        ),
        OnboardingScreen(
            title = "Отслеживайте настроение",
            description = "Фиксируйте свое эмоциональное состояние и выявляйте закономерности",
            imageRes = R.drawable.second_photo
        ),
        OnboardingScreen(
            title = "Контролируйте сон",
            description = "Анализируйте качество сна и получайте персональные рекомендации",
            imageRes = R.drawable.third_photo
        )
    )

    var currentPage by remember { mutableIntStateOf(0) }
    val isLastPage = currentPage == 2 // Пример для 3 экранов

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Индикатор прогресса
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            onboardingScreens.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                        .background(
                            color = if (index == currentPage) colorResource(R.color.blue_main)
                            else colorResource(R.color.gray).copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(Modifier.size(24.dp))

        // Контент текущего экрана
        Text(
            text = onboardingScreens[currentPage].title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFamily,
            color = colorResource(R.color.blue_main),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.size(4.dp))

        Text(
            text = onboardingScreens[currentPage].description,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(R.color.gray),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.size(16.dp))

        // Изображение
        Image(
            painter = painterResource(onboardingScreens[currentPage].imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(40.dp))

        // Единственная кнопка "Старт" (всегда активна)
        Button(
            onClick = {
                if (!isLastPage) {
                    currentPage++
                } else {
                    navController.navigate("start_screen")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.blue_main),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = if (isLastPage)  "Начать" else "Далее",
                fontSize = 16.sp
            )
        }
    }
}

data class OnboardingScreen(
    val title: String,
    val description: String,
    val imageRes: Int
)