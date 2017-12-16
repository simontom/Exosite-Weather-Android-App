package cz.saymon.android.exositeoneplatformrpc.ui

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import cz.saymon.android.exositeoneplatformrpc.R

interface SnackbarDisplayer {

    fun snackbarCoordinatorLayout(): CoordinatorLayout

    fun showSnackbar(text: String, showDuration: Int = Snackbar.LENGTH_SHORT,
                     textColor: Int? = null, backgroundColorRes: Int? = null) {
        val snackbar = Snackbar.make(snackbarCoordinatorLayout(), text, showDuration)
        applyColors(snackbar.view, textColor, backgroundColorRes)
        snackbar.show()
    }

    fun showSnackbarError(text: String, showDuration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(text, showDuration, R.color.material_grey200, R.color.material_red900)
    }

    fun showSnackbarWarning(text: String, showDuration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(text, showDuration, R.color.material_grey800, R.color.material_yellow600)
    }

    fun showSnackbarInfo(text: String, showDuration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(text, showDuration, R.color.material_grey200, R.color.material_cyan900)
    }

    fun showSnackbar(textRes: Int, showDuration: Int = Snackbar.LENGTH_SHORT,
                     textColorRes: Int? = null, backgroundColorRes: Int? = null) {
        val snackbar = Snackbar.make(snackbarCoordinatorLayout(), textRes, showDuration)
        applyColors(snackbar.view, textColorRes, backgroundColorRes)
        snackbar.show()
    }

    fun showSnackbarError(textRes: Int, showDuration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(textRes, showDuration, R.color.material_grey200, R.color.material_red900)
    }

    fun showSnackbarWarning(textRes: Int, showDuration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(textRes, showDuration, R.color.material_grey800, R.color.material_yellow600)
    }

    fun showSnackbarInfo(textRes: Int, showDuration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(textRes, showDuration, R.color.material_grey200, R.color.material_cyan900)
    }

    private fun applyColors(snackbarView: View, textColorRes: Int? = null, backgroundColorRes: Int? = null) {
        backgroundColorRes?.let {
            snackbarView.setBackgroundColor(ContextCompat.getColor(snackbarView.context, it))
        }
        textColorRes?.let {
            snackbarView
                    .findViewById<TextView>(android.support.design.R.id.snackbar_text)
                    .setTextColor(ContextCompat.getColor(snackbarView.context, it))
        }
    }

}
