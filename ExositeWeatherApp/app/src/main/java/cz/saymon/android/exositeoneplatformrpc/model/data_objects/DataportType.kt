package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.utils.contains


enum class DataportType(val unit: String) {
    TEMPERATURE("°C"),
    HUMIDITY("%"),
    PRESSURE("hPa"),
    VOLTAGE("mV"),
    UNKNOWN("--");

    companion object {
        private val TEMPERATURE_TYPE = Regex("tem.+", RegexOption.IGNORE_CASE)
        private val HUMIDITY_TYPE = Regex("hum.+", RegexOption.IGNORE_CASE)
        private val PRESSURE_TYPE = Regex("pre.+", RegexOption.IGNORE_CASE)
        private val VOLTAGE_TYPE = Regex("vol.+", RegexOption.IGNORE_CASE)

        fun parseFrom(dataportId: String): DataportType = when (dataportId) {
            in TEMPERATURE_TYPE -> TEMPERATURE
            in HUMIDITY_TYPE -> HUMIDITY
            in PRESSURE_TYPE -> PRESSURE
            in VOLTAGE_TYPE -> VOLTAGE
            else -> UNKNOWN
        }
    }
}
