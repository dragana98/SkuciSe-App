package com.blackbyte.skucise.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("com.blackbyte.skucise", Context.MODE_PRIVATE)
    private val AUTH_TOKEN = "authToken"
    private val USERNAME = "username"
    var authToken: String?
        get() = preferences.getString(AUTH_TOKEN, "unset")
        set(value) = preferences.edit().putString(AUTH_TOKEN, value).apply()
    var username: String?
        get() = preferences.getString(USERNAME, "unset")
        set(value) = preferences.edit().putString(USERNAME, value).apply()
}