package cz.saymon.android.exositeoneplatformrpc.model.data_objects

enum class DataportStatus(private val status: String) {
    OK("ok"),
    INVALID("invalid"),
    UNKNOWN_ERROR("unknown");

    companion object {
        private val values = values()
        fun from(status: String) = values.firstOrNull { status.equals(it.status, true) }
                ?: UNKNOWN_ERROR
    }
}
