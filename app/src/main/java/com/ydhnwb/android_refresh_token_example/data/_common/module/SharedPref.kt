package com.ydhnwb.android_refresh_token_example.data._common.module

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ydhnwb.android_refresh_token_example.domain.login.entity.LoginEntity
import java.util.*

@Suppress("UNCHECKED_CAST")
class SharedPrefs (private val context: Context) {
    companion object {
        private const val PREF = "AndroidRefTokenExample"
        private const val PREF_USER = "user_data"
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)


    fun saveUserDataAsJson(loginEntity: LoginEntity){
        val json = Gson().toJson(loginEntity)
        put(PREF_USER, json)
    }

    fun updateAccessToken(newAccessToken: String, newAccessTokenExpiredAt: Date){
        val currentUser = getUserData()
        if (currentUser != null){
            val c = currentUser.copy(accessToken = newAccessToken, accessTokenExpiredAt = newAccessTokenExpiredAt)
            saveUserDataAsJson(c)
        }
    }

    fun getUserData(): LoginEntity? {
        val userInJSON = get(PREF_USER, String::class.java)
        return Gson().fromJson(userInJSON, LoginEntity::class.java)
    }

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPref.edit().run {
            remove(PREF_USER)
        }.apply()
    }
}