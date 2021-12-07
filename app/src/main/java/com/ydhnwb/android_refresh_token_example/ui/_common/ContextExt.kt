package com.ydhnwb.android_refresh_token_example.ui._common

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("OK"){ dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}