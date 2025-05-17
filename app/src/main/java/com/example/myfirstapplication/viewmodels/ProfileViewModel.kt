package com.example.myfirstapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.classes.DoctorProfile
import com.example.myfirstapplication.classes.UserProfile
import com.example.myfirstapplication.whoVisit
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    private val _doctorProfile = MutableStateFlow<DoctorProfile?>(null)
    val doctorProfile: StateFlow<DoctorProfile?> = _doctorProfile

    private val _role = MutableStateFlow<String?>(null)
    val role: StateFlow<String?> = _role

    /** Загружает профиль по userId */
    fun loadProfile(userId: String) {

        if(whoVisit == "Пациент"){
            viewModelScope.launch {
                try {
                    // Требуется зависимость: implementation "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1"
                    val doc = db.collection("users")
                        .document(userId)
                        .get()
                        .await()

                    if (doc.exists()) {
                        _userProfile.value = UserProfile(
                            fullName   = doc.getString("fullName")  ?: "",
                            birthDate  = doc.getString("birthDate") ?: "",
                            phone      = doc.getString("phone")     ?: "",
                            address    = doc.getString("address")   ?: "",
                            doctor     = doc.getString("doctor")    ?: "",
                            diagnosis  = doc.getString("diagnosis") ?: "",
                            bloodGroup = doc.getString("bloodGroup")?: ""
                        )
                    } else {
                        // Документ не найден — пусть модель будет "пустой", но не null
                        _userProfile.value = UserProfile(
                            fullName   = "",
                            birthDate  = "",
                            phone      = "",
                            address    = "",
                            doctor     = "",
                            diagnosis  = "",
                            bloodGroup = ""
                        )
                    }
                } catch (e: Exception) {
                    Log.e("ProfileViewModel", "Ошибка загрузки профиля", e)
                    _userProfile.value = UserProfile(
                        fullName   = "",
                        birthDate  = "",
                        phone      = "",
                        address    = "",
                        doctor     = "",
                        diagnosis  = "",
                        bloodGroup = ""
                    )
                }
            }
        }
        else{
            viewModelScope.launch {
                try {
                    val doc = db.collection("users")
                        .document(userId)
                        .get()
                        .await()

                    if (doc.exists()) {
                        _doctorProfile.value = DoctorProfile(
                            fullName   = doc.getString("fullName")  ?: "",
                            phone      = doc.getString("phone")     ?: "",
                            job = doc.getString("job") ?: "",
                            organization = doc.getString("organization") ?: ""
                        )
                    } else {
                        _doctorProfile.value = DoctorProfile(
                            fullName   = "",
                            phone      = "",
                            job =  "",
                            organization = ""
                            )
                    }
                } catch (e: Exception) {
                    Log.e("ProfileViewModel", "Ошибка загрузки профиля", e)
                    _doctorProfile.value = DoctorProfile(
                        fullName   = "",
                        phone      = "",
                        job =  "",
                        organization = ""
                    )
                }
            }
        }
    }

    fun loadProfileByPhone(phoneNumber: String) {
        Log.d("ProfileViewModel", "=== loadProfileByPhone START ===")
        Log.d("ProfileViewModel", "whoVisit = '$whoVisit', phoneNumber = '$phoneNumber'")

        if (whoVisit == "Врач") {
            viewModelScope.launch {
                try {
                    Log.d("ProfileViewModel", "Отправляем запрос в Firestore...")
                    val querySnapshot = db.collection("users")
                        .whereEqualTo("phone", phoneNumber)
                        .get()
                        .await()
                    Log.d("ProfileViewModel", "Получен QuerySnapshot")

                    // Логируем, сколько документов вернулось и их ID
                    val count = querySnapshot.size()
                    val ids   = querySnapshot.documents.map { it.id }
                    Log.d("ProfileViewModel", "Найдено документов: $count")
                    Log.d("ProfileViewModel", "IDs: $ids")

                    // Логируем содержимое каждого документа
                    querySnapshot.documents.forEachIndexed { index, doc ->
                        Log.d("ProfileViewModel", "Документ #$index (id='${doc.id}'): ${doc.data}")
                    }

                    // Выбираем первый документ (если есть)
                    val doc = querySnapshot.documents.firstOrNull()
                    if (doc != null && doc.exists()) {
                        Log.d("ProfileViewModel", "Документ выбран для разбора: id='${doc.id}'")
                        _userProfile.value = UserProfile(
                            fullName   = doc.getString("fullName")  ?: "",
                            birthDate  = doc.getString("birthDate") ?: "",
                            phone      = doc.getString("phone")     ?: "",
                            address    = doc.getString("address")   ?: "",
                            doctor     = doc.getString("doctor")    ?: "",
                            diagnosis  = doc.getString("diagnosis") ?: "",
                            bloodGroup = doc.getString("bloodGroup")?: ""
                        )
                        Log.d("ProfileViewModel", "UserProfile установлен: ${_userProfile.value}")
                    } else {
                        Log.d("ProfileViewModel", "Документы с таким номером не найдены, сбрасываем в пустой профиль")
                        _userProfile.value = UserProfile("", "", "", "", "", "", "")
                    }
                } catch (e: Exception) {
                    Log.e("ProfileViewModel", "Ошибка при поиске профиля по телефону", e)
                    _userProfile.value = UserProfile("", "", "", "", "", "", "")
                } finally {
                    Log.d("ProfileViewModel", "=== loadProfileByPhone END ===")
                }
            }
        } else {
            Log.d("ProfileViewModel", "whoVisit != 'Пациент', пропускаем loadProfileByPhone для пациента")
        }
    }

    fun updateDoctorProfile(userId: String, profile: DoctorProfile, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                db.collection("users")
                    .document(userId)
                    .set(
                        mapOf(
                            "fullName"     to profile.fullName,
                            "phone"        to profile.phone,
                            "job"          to profile.job,
                            "organization" to profile.organization
                        ),
                        SetOptions.merge()
                    )
                    .await()
                onResult(true)
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Ошибка сохранения профиля", e)
                onResult(false)
            }
        }
    }

    fun updateUserProfileByPhone(
        phoneNumber: String,
        profile: UserProfile,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("users")
                    .whereEqualTo("phone", phoneNumber)
                    .get()
                    .await()
                val doc = snapshot.documents.firstOrNull()
                if (doc != null) {
                    val success = saveMapToDoc(doc.id, mapOf(
                        "fullName" to profile.fullName,
                        "birthDate" to profile.birthDate,
                        "phone" to profile.phone,
                        "address" to profile.address,
                        "doctor" to profile.doctor,
                        "diagnosis" to profile.diagnosis,
                        "bloodGroup" to profile.bloodGroup
                    ))
                    onResult(success)
                } else {
                    Log.e("ProfileViewModel", "Пользователь с телефоном $phoneNumber не найден")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Ошибка сохранения профиля по телефону", e)
                onResult(false)
            }
        }
    }

    fun loadUserRole(userId: String) {
        viewModelScope.launch {
            try {
                val doc = db.collection("users")
                    .document(userId)
                    .get()
                    .await()

                if (doc.exists()) {
                    _role.value = doc.getString("role") ?: ""
                } else {
                    _role.value = null
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Ошибка загрузки роли пользователя", e)
                _role.value = null
            }
        }
    }

    private suspend fun saveMapToDoc(
        docId: String,
        data: Map<String, Any?>
    ): Boolean {
        return try {
            db.collection("users")
                .document(docId)
                .set(data, SetOptions.merge())
                .await()
            true
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Ошибка сохранения документа $docId", e)
            false
        }
    }
}
