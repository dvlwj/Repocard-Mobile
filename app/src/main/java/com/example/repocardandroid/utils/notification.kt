package com.example.repocardandroid.utils

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, text: String): Toast {
    val duration = Toast.LENGTH_SHORT
    return Toast.makeText(context, text, duration)
}