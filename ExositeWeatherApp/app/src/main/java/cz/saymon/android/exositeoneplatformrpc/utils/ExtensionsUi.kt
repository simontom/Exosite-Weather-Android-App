package cz.saymon.android.exositeoneplatformrpc.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import cz.saymon.android.exositeoneplatformrpc.App
import cz.saymon.android.exositeoneplatformrpc.di.components.AppComponent

// Activity Extensions
///////////////////////
val Activity.app: App
    get() = application as App

val Activity.appComponent: AppComponent
    get() = app.appComponent

fun Activity.toast(text: String, showDuration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, text, showDuration).show()

fun Activity.toast(textResource: Int, showDuration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, textResource, showDuration).show()

fun Activity.hideSoftKeyboard() {
    if (this.currentFocus != null) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
    }
}

// Fragment Extensions
///////////////////////
fun Fragment.toast(text: String, showDuration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this.context!!, text, showDuration).show()

fun Fragment.toast(textResource: Int, showDuration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this.context!!, textResource, showDuration).show()

val Fragment.appComponent: AppComponent
    get() = this.getActivity()!!.appComponent

inline fun <reified T> Fragment.activityAs(): T? {
    return this.activity!! as? T
}

// View Extensions
///////////////////////
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.getColor(@ColorRes color: Int) = ContextCompat.getColor(context, color)

fun View.colorAsDrawable(@ColorRes color: Int): ColorDrawable {
    val colorValue = getColor(color)
    return ColorDrawable(colorValue)
}