package cz.saymon.android.exositeoneplatformrpc.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import cz.saymon.android.exositeoneplatformrpc.App
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
private val dateFormatter = SimpleDateFormat("dd/MM HH:mm")

val Activity.app: App
    get() = application as App

fun Activity.toast(text: String, showDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, showDuration).show()
}

fun Activity.toast(textResource: Int, showDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textResource, showDuration).show()
}

operator fun Regex.contains(text: CharSequence) = matches(text)

fun Long.toFormattedDate() = dateFormatter.format(Date(this))
