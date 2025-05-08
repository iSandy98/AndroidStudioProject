package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R

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
    }
}
val H2style = TextStyle(fontSize = 16.sp, fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium, color = Color(0xFF4B84F1))

@Composable
fun EmojiSlider(){
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ваше настроение на сегодня", style = H2style)
        Spacer(Modifier.size(16.dp))
        Slider(
            value = sliderPosition,
            onValueChange = {sliderPosition = it},
            steps = 9,
            valueRange = 0f..10f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.blue_main),
                activeTrackColor = colorResource(R.color.blue_main)
            )
        )
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
        Spacer(Modifier.size(30.dp))
        Text(
            text = "Качество сна", fontSize = 12.sp,
            fontWeight = FontWeight.Normal, fontFamily = robotoFamily,
            color = colorResource(R.color.gray)
        )
        Spacer(Modifier.size(16.dp))
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
fun Feedback() {

}


