package cz.saymon.android.exositeoneplatformrpctest.model.data_objects

enum class DataportLocation {
    private val ALIAS_TEMPBED = "temBed"
    private val ALIAS_HUMBED = "humBed"
    private val ALIAS_TEMPBAT = "temBat"
    private val ALIAS_HUMBAT = "humBat"
    private val ALIAS_TEMPLIV = "temLiv"
    private val ALIAS_HUMLIV = "humLiv"
    private val ALIAS_PREHAL = "preHal"
    private val ALIAS_TEMPHAL = "temHal";

    companion object {
        fun of(location: String): DataportLocation = when(location) {
            "ok" -> OK
            "invalid" -> INVALID
            else -> UNKNOWN_ERROR
        }
    }
}