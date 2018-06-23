package cz.saymon.android.exositeoneplatformrpc.model.data_objects


enum class DataportType(typePrefix: String,
                        val unit: String,
                        private val regex: Regex = Regex("$typePrefix.+")) {

    TEMPERATURE("tem", "°C"),
    HUMIDITY("hum", "%"),
    PRESSURE("pre", "hPa"),
    LIGHT("lig", "lx"),
    UV("uv", "uv"),
    VOLTAGE("vol", "mV"),
    UNKNOWN("_ignored_", "--");

    companion object {
        fun from(dataportId: String) =
                values().firstOrNull { it.regex.matches(dataportId) }
                        ?: UNKNOWN
    }
}
