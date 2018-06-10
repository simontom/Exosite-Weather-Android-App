package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.utils.contains

enum class DataportLocation(locationSuffix: String,
                            val locationName: String,
                            private val regex: Regex = Regex(".+$locationSuffix")) {

    BEDROOM("Bed", "Bedroom"),
    BATHROOM("Bat", "Bathroom"),
    LIVING_ROOM("Liv", "Living Room"),
    HALLWAY("Hal", "Hallway"),
    OUTSIDE("Out", "Outside"),
    OUTSIDE2("Out2", "Outside 2"),
    BEDROOM2("Bed2", "Mrkvicka"),
    UNKNOWN("#UNKNOWN#", "Unknown");

    companion object {
        fun from(dataportId: String) =
                values().firstOrNull { dataportId in it.regex }
                        ?: UNKNOWN
    }
}
