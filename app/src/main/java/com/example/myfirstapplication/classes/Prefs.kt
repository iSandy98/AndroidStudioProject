package com.example.myfirstapplication.classes

import android.content.Context
import com.example.myfirstapplication.App

object Prefs {
    private const val NAME = "my_app_prefs"
    private const val KEY_USER_ID = "key_user_id"

    private fun prefs(context: Context) =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var userId: String?
        get() = prefs(App.instance).getString(KEY_USER_ID, null)
        set(value) {
            prefs(App.instance)
                .edit()
                .putString(KEY_USER_ID, value)
                .apply()
        }
}