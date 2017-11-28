package cz.saymon.android.exositeoneplatformrpctest.utils

import android.app.Activity
import android.widget.Toast
import cz.saymon.android.exositeoneplatformrpctest.App

val Activity.app: App
    get() = application as App

fun Activity.toast(text: String, showDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, showDuration).show()
}

fun Activity.toast(textResource: Int, showDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textResource, showDuration).show()
}

operator fun Regex.contains(text: CharSequence) = matches(text)
