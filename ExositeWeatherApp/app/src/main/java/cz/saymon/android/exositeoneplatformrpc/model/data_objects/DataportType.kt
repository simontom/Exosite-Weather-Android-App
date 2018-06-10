package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.utils.contains


enum class DataportType(private val regex: Regex, val unit: String) {
    TEMPERATURE(Regex("tem.+"), "°C"),
    HUMIDITY(Regex("hum.+"), "%"),
    PRESSURE(Regex("pre.+"), "hPa"),
    LIGHT(Regex("lig.+"), "lx"),
    UV(Regex("uv.+"), "uv"),
    VOLTAGE(Regex("vol.+"), "mV"),
    UNKNOWN(Regex("_ignored_"), "--");

    companion object {
        fun from(dataportId: String): DataportType = when (dataportId) {
            in TEMPERATURE.regex -> TEMPERATURE
            in HUMIDITY.regex -> HUMIDITY
            in PRESSURE.regex -> PRESSURE
            in VOLTAGE.regex -> VOLTAGE
            else -> UNKNOWN
        }
    }
}
