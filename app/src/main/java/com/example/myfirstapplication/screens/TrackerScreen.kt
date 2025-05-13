package com.example.myfirstapplication.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H1styleVer2
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H3styleVer3
import com.example.myfirstapplication.H4styleVer3
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.R


@Composable
fun TrackerScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(start = 40.dp, end = 40.dp, top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ){
        item{
            EmojiSlider()
        }
        item{
            DreamSlider()
        }
        item {
            DrugTracker(navController)
        }
        item {
            Feedback()
        }
    }
}
val H2style = TextStyle(
    fontSize = 16.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium, color = Color(0xFF4B84F1)
)

@Composable
fun EmojiSlider(){
    var sliderPosition by remember { mutableFloatStateOf(4f) } // Начинаем с середины
    val emojis = listOf("😭", "😢", "😞", "😐", "🙂", "😊", "😄", "😁", "🤩", "🥰")

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ваше настроение на сегодня",
            fontSize = 20.sp,
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.blue_main)
        )

        Spacer(Modifier.size(16.dp))

        // Отображение текущего смайлика
        Text(
            text = emojis[sliderPosition.toInt()],
            fontSize = 30.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Слайдер
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            steps = 8, // 10 значений = 9 шагов между ними
            valueRange = 0f..9f, // Чтобы точно соответствовать индексам emojis
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.blue_main),
                activeTrackColor = colorResource(R.color.blue_main)
            )
        )

        // Подпись под слайдером
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Плохо", color = colorResource(R.color.gray))
            Text("Отлично", color = colorResource(R.color.gray))
        }

        Spacer(Modifier.size(30.dp))

        Button(
            onClick = { /* Сохранение значения */ },
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
fun DreamSlider() {
    var durationSliderPosition by remember { mutableFloatStateOf(0f) }
    var qualitySliderPosition by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Как прошел Ваш сон?", fontSize = 20.sp,
            fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
            color = colorResource(R.color.blue_main)
        )
        Spacer(Modifier.size(5.dp))
        Text(
            text = "Длительность сна",
            fontSize = 16.sp, fontFamily = robotoFamily,
            fontWeight = FontWeight.Normal, color = Color(0xFF8F9090),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(20.dp))
        //Слайдер Длительность сна
        Slider(
            value = durationSliderPosition,
            onValueChange = { durationSliderPosition = it },
            steps = 9,
            valueRange = 0f..10f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.blue_main),
                activeTrackColor = colorResource(R.color.blue_main)
            )
        )
        // Подпись под слайдером
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("1 ч", color = colorResource(R.color.gray))
            Text("10 ч", color = colorResource(R.color.gray))
        }
        Spacer(Modifier.size(50.dp))
        Text(
            text = "Качество сна по 10-тибальной шкале",
            fontSize = 16.sp, fontFamily = robotoFamily,
            fontWeight = FontWeight.Normal, color = Color(0xFF8F9090),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(20.dp))
        //Слайдер качество сна
        Slider(
            value = qualitySliderPosition,
            onValueChange = { qualitySliderPosition = it },
            steps = 9,
            valueRange = 0f..10f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.blue_main),
                activeTrackColor = colorResource(R.color.blue_main)
            )
        )
        //Подпись под слайдером
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Плохо", color = colorResource(R.color.gray))
            Text("Отлично", color = colorResource(R.color.gray))
        }
        Spacer(Modifier.size(30.dp))
        Button(
            onClick = {},
            modifier = Modifier.height(48.dp),
            colors = ButtonColors(
                containerColor = colorResource(R.color.blue_main),
                contentColor = colorResource(R.color.white),
                disabledContainerColor = colorResource(R.color.blue_disable),
                disabledContentColor = colorResource(R.color.gray)
            )
        ) {
            Text(text = "Сохранить")
        }
    }
}


@Composable
fun DrugTracker(navController: NavHostController) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Трекер приема лекарств", fontSize = 20.sp,
                fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
                color = colorResource(R.color.blue_main))
            Spacer(Modifier.size(30.dp))
            Image(
                painter = painterResource(id = R.drawable.icon_drug),
                contentDescription = "Иконка лекарства",
                modifier = Modifier.size(50.dp)
            )

            Spacer(Modifier.size(15.dp))

            Text(
                text = "Пожалуйста, отметьте прием лекарств",
                style = H4styleVer3,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(30.dp))

            Button(
                onClick = {
                    navController.navigate("drugs_screen")
                },
                modifier = Modifier.height(48.dp),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.blue_main),
                    contentColor = colorResource(R.color.white),
                    disabledContainerColor = colorResource(R.color.blue_disable),
                    disabledContentColor = colorResource(R.color.gray)
                )
            ) {
                Text(text = "Отметить лекарства")
            }
        }
}

@Composable
fun Feedback() {
    var feedbackText by remember { mutableStateOf("") }
    val colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,    // Белый фон в фокусированном состоянии
        unfocusedContainerColor = Color.White,  // Белый фон в обычном состоянии
        disabledContainerColor = Color.White,   // Белый фон в отключенном состоянии

        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        disabledBorderColor = Color.Transparent
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Отклик лечащему врачу", fontSize = 20.sp,
            fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
            color = colorResource(R.color.blue_main))
        Spacer(Modifier.size(10.dp))
        Text("Пожеланию можете оставить сообщение Вашему лечащему врачу:", style = H4styleVer3,
            textAlign = TextAlign.Center)
        Spacer(Modifier.size(16.dp))
        TextField(value = feedbackText, onValueChange = { feedbackText = it},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily, fontWeight = FontWeight.Normal),
            placeholder = { Text(text = "Начни писать здесь", style = H5style)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = colors
            )
        Spacer(Modifier.size(12.dp))
        //Календарь
        Spacer(Modifier.size(15.dp))
        Button(
            onClick = {},
            modifier = Modifier.height(48.dp),
            colors = ButtonColors(
                containerColor = colorResource(R.color.blue_main),
                contentColor = colorResource(R.color.white),
                disabledContainerColor = colorResource(R.color.blue_disable),
                disabledContentColor = colorResource(R.color.gray)
            )
        ) {
            Text(text = "Отправить")
        }
        Spacer(Modifier.size(30.dp))
    }
}


