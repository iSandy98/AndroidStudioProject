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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.R
import com.example.myfirstapplication.whoVisit
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myfirstapplication.classes.AuthState
import com.example.myfirstapplication.classes.Prefs
import com.example.myfirstapplication.viewmodels.AuthViewModel
import com.example.myfirstapplication.viewmodels.UserViewModel


@Composable
fun SignUpScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val role = if (whoVisit == "Врач") "doctor" else "patient"
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        Text(text = "Добро пожаловать в Moody!", fontSize = 20.sp, fontWeight = FontWeight.Medium, fontFamily = robotoFamily, color = colorResource(R.color.blue_main))
        Spacer(Modifier.size(3.dp))
        Text(text = "Зарегистрируйтесь, чтобы начать", fontSize = 12.sp, fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily, color = colorResource(R.color.black_500))
        Spacer(Modifier.size(32.dp))
        Text(text = "Ваше ФИО", fontSize = 12.sp, fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily, color = colorResource(R.color.gray))
        Spacer(Modifier.size(8.dp))
        TextField(
            value = fullName, onValueChange = { fullName = it},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily, fontWeight = FontWeight.Normal),
            placeholder = { Text(text = "Начните вводить ФИО", fontSize = 14.sp, color = colorResource(R.color.gray), fontFamily = robotoFamily) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Gray,
            )
        )
        Spacer(Modifier.size(12.dp))
        Text(text = "Номер телефона", fontSize = 12.sp, fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily, color = colorResource(R.color.gray))
        Spacer(Modifier.size(8.dp))
        TextField(
            value = phone,
            onValueChange = { phone = it},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(fontSize = 14.sp, fontFamily = robotoFamily),
            placeholder = { Text(text = "+7", fontSize = 14.sp, color = colorResource(R.color.gray), fontFamily = robotoFamily) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Gray

            )
        )
        Spacer(Modifier.size(32.dp))
        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            is AuthState.Error -> Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
            else -> { /* Idle or Success */ }
        }
        Box(
            Modifier.padding(start = 40.dp, end = 40.dp).fillMaxWidth()
        ) {
            Button(
                onClick = {
                    /*if(whoVisit == "Врач") {
                        navController.navigate("doctor_main_menu_screen")
                    }else{
                        navController.navigate("tracker_screen")
                    }*/
                    authViewModel.registerUser(fullName, phone, role)
                },
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
                modifier = Modifier.clickable {
                    navController.navigate("sign_in_screen")
                })
        }

        if (authState is AuthState.Success) {
            LaunchedEffect(authState) {

                val success = authState as AuthState.Success
                Prefs.userId = success.userId

                if (success.role == "doctor") navController.navigate("doctor_main_menu_screen")
                else navController.navigate("tracker_screen")
                authViewModel.resetState()
            }
        }

    }
}