package cz.saymon.android.exositeoneplatformrpctest.model.data_objects

import cz.saymon.android.exositeoneplatformrpctest.utils.contains

enum class DataportType {
    TEMPERATURE,
    HUMIDITY,
    PRESSURE,
    VOLTAGE,
    UNKNOWN;

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