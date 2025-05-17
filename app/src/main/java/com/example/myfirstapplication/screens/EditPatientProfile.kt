package com.example.myfirstapplication.screens
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.myfirstapplication.H3style
import com.example.myfirstapplication.R
import com.example.myfirstapplication.classes.UserProfile
import com.example.myfirstapplication.viewmodels.ProfileViewModel


@Composable
fun EditPatientProfile(
    navController: NavHostController,
    phone:String,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(phone) {
        if (phone.isNotBlank()) {
            profileViewModel.loadProfileByPhone(phone)
        }
    }

    val profile by profileViewModel.userProfile.collectAsState()

    var fullname by remember { mutableStateOf("") }
    var dateofbirth by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var doctor by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    var blood by remember { mutableStateOf("") }

    LaunchedEffect(profile) {
        profile?.let {
            fullname = it.fullName ?: ""
            dateofbirth = it.birthDate ?: ""
            phonenumber = it.phone ?: ""
            address = it.address ?: ""
            doctor = it.doctor ?: ""
            diagnosis = it.diagnosis ?: ""
            blood = it.bloodGroup ?: ""
        }
    }

    if (profile == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Редактирование профиля пациента",
            style = H3style,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(36.dp))
        Text(
            text = "ФИО пациента",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = fullname, onValueChange = { fullname = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = profile?.fullName ?: "",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Дата рождения",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = dateofbirth, onValueChange = { dateofbirth = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = profile?.birthDate ?: "",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
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
        TextField(
            value = phonenumber, onValueChange = { phonenumber = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = profile?.phone ?: "",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Адрес проживания",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = address, onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = profile?.address ?: "",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Лечащий врач",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = doctor, onValueChange = { doctor = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = profile?.doctor ?: "",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Диагноз",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = diagnosis, onValueChange = { diagnosis = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = profile?.diagnosis ?: "",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Группа крови",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = robotoFamily,
            color = colorResource(
                R.color.gray
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = blood, onValueChange = { blood = it },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = profile?.bloodGroup ?: "",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = robotoFamily
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(30.dp))
        Box(
            Modifier.padding(start = 40.dp, end = 40.dp).fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val updated = UserProfile(
                        fullName = fullname,
                        birthDate = dateofbirth,
                        phone = phonenumber,
                        address = address,
                        doctor = doctor,
                        diagnosis = diagnosis,
                        bloodGroup = blood
                    )
                    profileViewModel.updateUserProfileByPhone(phone, updated) { success ->
                        if (success) {
                            Toast.makeText(context, "Профиль сохранен", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Ошибка при сохранении", Toast.LENGTH_SHORT).show()
                        }
                    }
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
                Text(text = "Сохранить")
            }
        }
    }
}

@Preview
@Composable
fun EditPatientProfilePreview(){
    EditPatientProfile(
        NavHostController(LocalContext.current),
        ""
    )
}