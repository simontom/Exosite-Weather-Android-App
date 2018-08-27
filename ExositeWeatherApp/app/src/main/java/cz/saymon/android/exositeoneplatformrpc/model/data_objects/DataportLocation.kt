package cz.saymon.android.exositeoneplatformrpc.model.data_objects

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
        private val values = values()
        fun from(dataportId: String) =
                values.firstOrNull { it.regex.matches(dataportId) }
                        ?: UNKNOWN
    }
}
