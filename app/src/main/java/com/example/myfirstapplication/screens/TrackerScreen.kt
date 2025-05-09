package com.example.myfirstapplication.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.H5style
import com.example.myfirstapplication.R
import com.example.myfirstapplication.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun TrackerScreen(){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(start = 40.dp, end = 40.dp, top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ){
        item{
            EmojiSlider()
        }
        item{
            DreamSlider()
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
            fontSize = 16.sp,
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
            text = "Как прошел Ваш сон?", fontSize = 16.sp,
            fontFamily = robotoFamily, fontWeight = FontWeight.Medium,
            color = colorResource(R.color.blue_main)
        )
        Spacer(Modifier.size(16.dp))
        Text(
            text = "Длительность сна", fontSize = 12.sp,
            fontWeight = FontWeight.Normal, fontFamily = robotoFamily,
            color = colorResource(R.color.gray)
        )
        Spacer(Modifier.size(16.dp))
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
        Spacer(Modifier.size(30.dp))
        Text(
            text = "Качество сна по 10-тибальной шкале", fontSize = 12.sp,
            fontWeight = FontWeight.Normal, fontFamily = robotoFamily,
            color = colorResource(R.color.gray)
        )
        Spacer(Modifier.size(16.dp))
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
fun DrugTracker() {
    Text("Трекер приема лекарств", style = H5style)
    Spacer(Modifier.size(30.dp))
    Image(
        painter = painterResource(id = R.drawable.icon_drug),
        contentDescription = "Иконка лекарства",
        modifier = Modifier.size(24.dp)
    )

    Spacer(modifier = Modifier.width(15.dp))

    Text(
        text = "Принято 2/3",
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(R.color.gray)
    )
    Spacer(modifier = Modifier.width(54.dp))
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
        Text(text = "Подробнее")
    }
}

@Composable
fun Feedback() {
    var feedbackText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Отклик лечащему врачу", style = H2style)
        Spacer(Modifier.size(10.dp))
        Text("Пожеланию можете оставить сообщение Вашему лечащему врачу.", style = H5style)
        Spacer(Modifier.size(16.dp))
        TextField(value = feedbackText, onValueChange = { feedbackText = it},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily, fontWeight = FontWeight.Normal),
            placeholder = { Text(text = "Начни писать здесь", style = H5style)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(Modifier.size(12.dp))
        //Календарь
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
            Text(text = "Отправить")
        }
    }
}


