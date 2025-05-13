package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H1styleVer2
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.R


@Composable

fun EditDoctorProfile(navController: NavHostController) {
    var fullname by remember { mutableStateOf("") }
    var dateofbirth by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,    // Белый фон в фокусированном состоянии
        unfocusedContainerColor = Color.White,  // Белый фон в обычном состоянии
        disabledContainerColor = Color.White,   // Белый фон в отключенном состоянии

        focusedBorderColor = Color.Gray,        // Серая обводка в фокусированном состоянии
        unfocusedBorderColor = Color.Gray,      // Серая обводка в обычном состоянии
        disabledBorderColor = Color.LightGray   // Светло-серая обводка в отключенном состоянии
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Редактирование профиля",
            style = H1styleVer2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(36.dp))
        Text(
            text = "Ваше ФИО",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = fullname, onValueChange = { fullname = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = "Никитина Антонина Николаевна",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = colors
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Должность",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = dateofbirth, onValueChange = { dateofbirth = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = "Психиатр Нарколог",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = colors
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Номер телефона",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = phonenumber, onValueChange = { phonenumber = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = "7 924 867 55 45",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = colors
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Медицинская организация",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = address, onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = "РБ №2 ЦЭМП",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = colors
        )
        Spacer(modifier = Modifier.size(30.dp))
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
                Text(text = "Сохранить")
            }
        }
    }
}