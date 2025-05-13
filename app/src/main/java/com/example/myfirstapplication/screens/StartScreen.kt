package com.example.myfirstapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.R
import com.example.myfirstapplication.whoVisit

val robotoFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_medium, FontWeight.Medium)
)

@Composable
fun StartScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(start = 40.dp, end = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Прежде чем начать", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = robotoFamily, color = colorResource(R.color.blue_main))
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = "Выберите свою роль для входа", fontSize = 12.sp, fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily, color = colorResource(R.color.gray))
        Spacer(modifier = Modifier.size(50.dp))
        Button(
            onClick = {
                navController.navigate("entry_screen")
                whoVisit = "Врач"
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonColors(
                containerColor = colorResource(R.color.blue_main),
                contentColor = colorResource(R.color.white),
                disabledContainerColor = colorResource(R.color.blue_disable),
                disabledContentColor = colorResource(R.color.gray)
            )
        ) {
            Text(text = "Врач")
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = {
                navController.navigate("entry_screen")
                whoVisit = "Пациент"
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonColors(
                containerColor = colorResource(R.color.blue_main),
                contentColor = colorResource(R.color.white),
                disabledContainerColor = colorResource(R.color.blue_disable),
                disabledContentColor = colorResource(R.color.gray)
            )
        ) {
            Text(text = "Пациент")
        }
    }
}