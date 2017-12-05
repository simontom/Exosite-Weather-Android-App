package cz.saymon.android.exositeoneplatformrpc.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.IntegerRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import cz.saymon.android.exositeoneplatformrpc.App
import cz.saymon.android.exositeoneplatformrpc.di.components.AppComponent
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
private val dateFormatter = SimpleDateFormat("dd/MM HH:mm")

val Activity.app: App
    get() = application as App

val Activity.appComponent: AppComponent
    get() = app.appComponent

fun Activity.toast(text: String, showDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, showDuration).show()
}

fun Activity.toast(textResource: Int, showDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textResource, showDuration).show()
}

fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

operator fun Regex.contains(text: CharSequence) = matches(text)

fun Long.toFormattedDate() = dateFormatter.format(Date(this))

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
