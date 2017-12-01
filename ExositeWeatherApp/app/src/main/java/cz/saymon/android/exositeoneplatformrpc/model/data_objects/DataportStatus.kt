package cz.saymon.android.exositeoneplatformrpc.model.data_objects

enum class DataportStatus {
    OK,
    INVALID,
    UNKNOWN_ERROR;

    companion object {
        private const val OK_STATUS = "ok"
        private const val INVALID_STATUS = "invalid"

        fun parseFrom(status: String): DataportStatus = when(status) {
            OK_STATUS -> OK
            INVALID_STATUS -> INVALID
            else -> UNKNOWN_ERROR
        }
    }
}
