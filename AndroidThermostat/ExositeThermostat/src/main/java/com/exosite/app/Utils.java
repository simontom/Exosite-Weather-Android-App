package com.exosite.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.saymon.exosite.weatherstation.R;

/**
 * Created by SaymonRey on 14.12.2015.
 * Meow
 */
class Utils {

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM  HH:mm");


    // Forbids You to instantiate this class
    ////////////////////////////////////////
    private Utils() {                    }


    static void setUpdateTextView(TextView textView, Date updateTime) {
        if (updateTime == null) {
            textView.setText("--");
        }
        else {
            textView.setText(dateFormat.format(updateTime));
        }
    }

    static void setTemperatureTextView(Activity activity, TextView textView, Double temperature, Double temperatureDiff) {
        if (temperature == null) {
            textView.setText("--");
            return;
        } else {
            textView.setText(String.format("%.1f°C   %+.1f°C", temperature, temperatureDiff));
        }

        if ((temperatureDiff != null) && (temperatureDiff >= 0.3d)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.temperature_rising));
        }
        else if ((temperatureDiff != null) && (temperatureDiff <= -0.3d)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.temperature_falling));
        }
        else {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.temperature_stable));
        }
    }

    static void setPressureTextView(TextView textView, Double pressure, Double pressureDiff) {
        if (pressure == null) {
            textView.setText("--");
        } else {
            textView.setText(String.format("%.1fhPa   %+.1fhPa", pressure, pressureDiff));
        }
    }

    static void setHumidityTextView(TextView textView, Double humidity, Double humidityDiff) {
        if (humidity == null) {
            textView.setText("--");
        } else {
            textView.setText(String.format("%.1f%%   %+.1f%%", humidity, humidityDiff));
        }
    }

    @SuppressLint ("SetTextI18n")
    static void setVoltageTextView(Activity activity, TextView textView, Integer voltage, Integer voltageDiff) {
        if (voltage == null) {
            textView.setText("--");
        }
        else {
            textView.setText(voltage + "mV   " + voltageDiff + "mV");
        }

        if ((voltage != null) && (voltage < 3800)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.voltage_alert));
        }
        else {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.voltage_normal));
        }
    }

    @SuppressLint ("SetTextI18n")
    static void setUvIndexTextView(Activity activity, TextView textView, Integer uvIndex, Integer uvIndexDiff) {
        if (uvIndex == null) {
            textView.setText("--");
        }
        else {
            textView.setText("UV:   " + uvIndex + "   " + uvIndexDiff);
        }

        if ((uvIndex != null) && (uvIndex <= 2)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.uv_low));
        }
        else if ((uvIndex != null) && (uvIndex <= 5)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.uv_moderate));
        }
        else if ((uvIndex != null) && (uvIndex <= 7)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.uv_high));
        }
        else if ((uvIndex != null) && (uvIndex <= 10)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.uv_veryHigh));
        }
        else if ((uvIndex != null) && (uvIndex > 10)) {
            textView.setBackgroundColor(activity.getResources().getColor(R.color.uv_extreme));
        }
    }

    @SuppressLint ("SetTextI18n")
    static void setLightTextView(TextView textView, Double light, Double lightDiff) {
        if (light == null) {
            textView.setText("--");
        }
        else {
            textView.setText(String.format("%.1flx   %+.1flx", light, lightDiff));
        }
    }

}
