package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.utils.contains

enum class DataportLocation(private val regex: Regex, val locationName: String) {
    BEDROOM(Regex(".+Bed", RegexOption.IGNORE_CASE), "Bedroom"),
    BATHROOM(Regex(".+Bat", RegexOption.IGNORE_CASE), "Bathroom"),
    LIVING_ROOM(Regex(".+Liv", RegexOption.IGNORE_CASE), "Living Room"),
    HALLWAY(Regex(".+Hal", RegexOption.IGNORE_CASE), "Hallway"),
    OUTSIDE(Regex(".+Out", RegexOption.IGNORE_CASE), "Outside"),
    OUTSIDE2(Regex(".+Out2", RegexOption.IGNORE_CASE), "Outside 2"),
    BEDROOM2(Regex(".+Bed2", RegexOption.IGNORE_CASE), "Mrkvicka"),
    UNKNOWN(Regex("_ignored_"), "Unknown");

    companion object {
        fun parseFrom(dataportId: String): DataportLocation = when(dataportId) {
            in BEDROOM.regex -> BEDROOM
            in BATHROOM.regex -> BATHROOM
            in LIVING_ROOM.regex -> LIVING_ROOM
            in HALLWAY.regex -> HALLWAY
            in OUTSIDE.regex -> OUTSIDE
            in OUTSIDE2.regex -> OUTSIDE2
            in BEDROOM2.regex -> BEDROOM2
            else -> UNKNOWN
        }
    }
}
