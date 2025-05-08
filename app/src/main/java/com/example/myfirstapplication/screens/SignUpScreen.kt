package com.example.myfirstapplication.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R

@Preview( showBackground = true)
@Composable
fun SignUpScreen () {

    var phone by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        Text(text = "Добро пожаловать в Moody!", fontSize = 18.sp, fontWeight = FontWeight.Medium, fontFamily = robotoFamily)
        Spacer(Modifier.size(8.dp))
        Text(text = "Зарегистрируйтесь, чтобы начать", fontSize = 12.sp, fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily, color = colorResource(R.color.black))
        Spacer(Modifier.size(16.dp))
        Text(text = "Ваше ФИО", fontSize = 12.sp, fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily, color = colorResource(R.color.gray))
        Spacer(Modifier.size(8.dp))
        TextField(value = fullname, onValueChange = { fullname = it},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily, fontWeight = FontWeight.Normal),
            placeholder = { Text(text = "Начните вводить ФИО", fontSize = 14.sp, color = colorResource(R.color.gray), fontFamily = robotoFamily) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )

        )
        Spacer(Modifier.size(8.dp))
        TextField(value = phone, onValueChange = { phone = it},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily),
            placeholder = { Text(text = "+7", fontSize = 14.sp, color = colorResource(R.color.gray), fontFamily = robotoFamily) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(Modifier.size(20.dp))
        Box(
            Modifier.padding(start = 40.dp, end = 40.dp).fillMaxWidth()
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.blue_main),
                    contentColor = colorResource(R.color.white),
                    disabledContainerColor = colorResource(R.color.blue_disable),
                    disabledContentColor = colorResource(R.color.gray)
                )
            ) {
                Text(text = "Зарегистрироваться")
            }
        }
        Spacer(Modifier.size(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Уже есть аккаунт? ", fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = robotoFamily)
            Text(text = "Войти", fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = robotoFamily, color = colorResource(R.color.blue_main),
                modifier = Modifier.clickable {  })
        }



    }
}