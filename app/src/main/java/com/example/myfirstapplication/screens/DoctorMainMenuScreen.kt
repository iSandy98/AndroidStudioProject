package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextFieldDefaults // Добавлен этот импорт
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.KeyboardType
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.H4styleVer2
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Preview(showBackground = true)
@Composable
fun DoctorMainMenu() {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок
        Text(
            text = "Список пациентов",
            style = H3style,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(19.dp))

        // Поле ввода (поиск)
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Введите запрос",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Здесь можно добавить действие при нажатии "Поиск"
                    println("Поиск: $query")
                }
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Подзаголовок "Результат поиска"
        Text(
            text = "Результат поиска",
            style = H4styleVer2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(3) { index ->
                Text(
                    text = "Элемент $index для запроса \"$query\"",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}


