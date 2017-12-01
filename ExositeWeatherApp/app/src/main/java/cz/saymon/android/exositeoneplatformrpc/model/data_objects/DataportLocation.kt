package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.utils.contains

enum class DataportLocation(private val regex: Regex) {
    BEDROOM(Regex(".+Bed", RegexOption.IGNORE_CASE)),
    BATHROOM(Regex(".+Bat", RegexOption.IGNORE_CASE)),
    LIVING_ROOM(Regex(".+Liv", RegexOption.IGNORE_CASE)),
    HALLWAY(Regex(".+Hal", RegexOption.IGNORE_CASE)),
    OUTSIDE(Regex(".+Out", RegexOption.IGNORE_CASE)),
    BEDROOM2(Regex(".+Bed2", RegexOption.IGNORE_CASE)),
    UNKNOWN(Regex("_ignored_"));

    companion object {
        fun parseFrom(dataportId: String): DataportLocation = when(dataportId) {
            in BEDROOM.regex -> BEDROOM
            in BATHROOM.regex -> BATHROOM
            in LIVING_ROOM.regex -> LIVING_ROOM
            in HALLWAY.regex -> HALLWAY
            in OUTSIDE.regex -> OUTSIDE
            in BEDROOM2.regex -> BEDROOM2
            else -> UNKNOWN
        }
    }
}
