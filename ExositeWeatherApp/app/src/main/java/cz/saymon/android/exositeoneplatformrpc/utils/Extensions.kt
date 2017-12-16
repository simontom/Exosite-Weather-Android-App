package cz.saymon.android.exositeoneplatformrpc.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
private val dateFormatter = SimpleDateFormat("dd/MM HH:mm")

operator fun Regex.contains(text: CharSequence) = matches(text)

fun Long.toFormattedDate() = dateFormatter.format(Date(this))
