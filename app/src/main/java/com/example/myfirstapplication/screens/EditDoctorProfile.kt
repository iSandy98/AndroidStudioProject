package com.example.myfirstapplication.screens

import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myfirstapplication.H1styleVer2
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.R
import com.example.myfirstapplication.classes.DoctorProfile
import com.example.myfirstapplication.classes.Prefs
import com.example.myfirstapplication.viewmodels.ProfileViewModel


@Composable
fun EditDoctorProfile(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val userId = remember { Prefs.userId ?: "" }

    LaunchedEffect(userId) {
        if (userId.isNotBlank()) {
            profileViewModel.loadProfile(userId)
        }
    }

    val profile by profileViewModel.doctorProfile.collectAsState()

    var fullname by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }
    var organization by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(profile) {
        profile?.let {
            fullname     = it.fullName ?: ""
            phonenumber  = it.phone ?: ""
            job          = it.job ?: ""
            organization = it.organization ?: ""
        }
    }

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

        if (profile == null) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Column
        }

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
            value = fullname,
            onValueChange = { fullname = it },
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
                    text = "ФИО",
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
            value = job, onValueChange = { job = it },
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
                    text = "Должность",
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
                    text = "Номер",
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
            value = organization, onValueChange = { organization = it },
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
                    text = "Организация",
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
                onClick = {
                    isSaving = true
                    profileViewModel.updateDoctorProfile(
                        userId,
                        DoctorProfile(
                            fullName = fullname,
                            phone = phonenumber,
                            job = job,
                            organization = organization
                        )
                    ) { success ->
                        isSaving = false
                        if (success) {
                            Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show()
                        } else {
                            // показать ошибку
                        }
                    }
                },
                enabled = !isSaving,
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
                if (isSaving) CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                Text(text = "Сохранить")
            }
        }
    }
}