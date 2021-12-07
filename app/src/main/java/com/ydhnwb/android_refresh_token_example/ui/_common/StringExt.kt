package com.ydhnwb.android_refresh_token_example.ui._common

import android.util.Patterns

fun String.isEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}