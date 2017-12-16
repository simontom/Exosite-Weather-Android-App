package cz.saymon.android.exositeoneplatformrpc.ui

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView

interface SnackbarDisplayer {

    fun snackbarCoordinatorLayout(): CoordinatorLayout

    fun showSnackbar(text: String, showDuration: Int = Snackbar.LENGTH_SHORT,
                     textColor: Int? = null, backgroundColorRes: Int? = null) {
        val snackbar = Snackbar.make(snackbarCoordinatorLayout(), text, showDuration)
        applyColors(snackbar.view, textColor, backgroundColorRes)
        snackbar.show()
    }

    fun showSnackbar(textRes: Int, showDuration: Int = Snackbar.LENGTH_SHORT,
                     textColorRes: Int? = null, backgroundColorRes: Int? = null) {
        val snackbar = Snackbar.make(snackbarCoordinatorLayout(), textRes, showDuration)
        applyColors(snackbar.view, textColorRes, backgroundColorRes)
        snackbar.show()
    }

    private fun applyColors(snackbarView: View, textColorRes: Int? = null, backgroundColorRes: Int? = null) {
        backgroundColorRes?.let { snackbarView.setBackgroundColor(it) }
        textColorRes?.let {
            snackbarView
                    .findViewById<TextView>(android.support.design.R.id.snackbar_text)
                    .setTextColor(it)
        }
    }
    
}
