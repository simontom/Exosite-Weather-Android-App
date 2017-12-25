package cz.saymon.android.exositeoneplatformrpc.utils

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
private val dateFormatter = SimpleDateFormat("yy/MM/dd HH:mm")

operator fun Regex.contains(text: CharSequence) = matches(text)

fun Long.toFormattedDate() = dateFormatter.format(Date(this))

fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable {
    compositeDisposable.add(this)
    return this
}
