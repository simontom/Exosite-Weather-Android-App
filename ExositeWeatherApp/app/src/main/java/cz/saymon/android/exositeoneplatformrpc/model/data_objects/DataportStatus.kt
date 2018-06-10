package cz.saymon.android.exositeoneplatformrpc.model.data_objects

enum class DataportStatus(private val status: String) {
    OK("ok"),
    INVALID("invalid"),
    UNKNOWN_ERROR("unknown");

    companion object {
        fun from(status: String): DataportStatus = when(status.toLowerCase()) {
            OK.status -> OK
            INVALID.status -> INVALID
            else -> UNKNOWN_ERROR
        }
    }
}
