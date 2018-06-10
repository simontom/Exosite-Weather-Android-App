package cz.saymon.android.exositeoneplatformrpc.model.data_objects


enum class DataportType(private val regex: Regex, val unit: String) {
    TEMPERATURE(Regex("tem.+"), "°C"),
    HUMIDITY(Regex("hum.+"), "%"),
    PRESSURE(Regex("pre.+"), "hPa"),
    LIGHT(Regex("lig.+"), "lx"),
    UV(Regex("uv.+"), "uv"),
    VOLTAGE(Regex("vol.+"), "mV"),
    UNKNOWN(Regex("_ignored_"), "--");

    companion object {
        fun from(dataportId: String) =
                values().firstOrNull { it.regex.matches(dataportId) }
                        ?: UNKNOWN
    }
}
