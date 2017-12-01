package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.utils.contains

enum class DataportLocation {
    BEDROOM,
    BATHROOM,
    LIVING_ROOM,
    HALLWAY,
    OUTSIDE,
    UNKNOWN;

    companion object {
        private val BEDROOM_LOCATION = Regex(".+Bed", RegexOption.IGNORE_CASE)
        private val BATHROOM_LOCATION = Regex(".+Bat", RegexOption.IGNORE_CASE)
        private val LIVING_ROOM_LOCATION = Regex(".+Liv", RegexOption.IGNORE_CASE)
        private val HALLWAY_LOCATION = Regex(".+Hal", RegexOption.IGNORE_CASE)
        private val OUTSIDE_LOCATION = Regex(".+Out", RegexOption.IGNORE_CASE)

        fun parseFrom(dataportId: String): DataportLocation = when(dataportId) {
            in BEDROOM_LOCATION -> BEDROOM
            in BATHROOM_LOCATION -> BATHROOM
            in LIVING_ROOM_LOCATION -> LIVING_ROOM
            in HALLWAY_LOCATION -> HALLWAY
            in OUTSIDE_LOCATION -> OUTSIDE
            else -> UNKNOWN
        }
    }
}