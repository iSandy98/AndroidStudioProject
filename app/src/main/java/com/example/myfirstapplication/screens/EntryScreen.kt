package com.example.myfirstapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R

@Preview (showBackground = true)
@Composable
fun EntryScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
            .paint(
            // Replace with your image id
            painterResource(id = R.drawable.backgroud),
            contentScale = ContentScale.FillBounds),

    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Заботьтесь о себе легко", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = robotoFamily)
            Text(text = "Ваш персональный трекер здоровья и связи с врачом", fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = robotoFamily, color = colorResource(R.color.gray))
            Spacer(modifier = Modifier.size(360.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.blue_main),
                    contentColor = colorResource(R.color.white),
                    disabledContainerColor = colorResource(R.color.blue_disable),
                    disabledContentColor = colorResource(R.color.gray)
                )
            ) {
                Text(text = "Регистрация")
            }
            Spacer(modifier = Modifier.size(12.dp))
            Row(){
                Text(text = "Уже есть аккаунт? ", fontSize = 14.sp, fontWeight = FontWeight.Normal, fontFamily = robotoFamily)
                Text(text = "Войти", fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = robotoFamily, color = colorResource(R.color.white),
                    modifier = Modifier.clickable {  })
            }
        }

    }
}