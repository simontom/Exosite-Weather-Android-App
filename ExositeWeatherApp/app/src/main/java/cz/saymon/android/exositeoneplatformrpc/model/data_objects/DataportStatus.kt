package cz.saymon.android.exositeoneplatformrpc.model.data_objects

enum class DataportStatus(private val status: String) {
    OK("ok"),
    INVALID("invalid"),
    UNKNOWN_ERROR("");

    companion object {
        fun parseFrom(status: String): DataportStatus = when(status) {
            OK.status -> OK
            INVALID.status -> INVALID
            else -> UNKNOWN_ERROR
        }
    }
}
