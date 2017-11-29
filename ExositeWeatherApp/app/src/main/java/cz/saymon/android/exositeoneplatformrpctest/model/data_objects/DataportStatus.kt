package cz.saymon.android.exositeoneplatformrpctest.model.data_objects

enum class ValueStatus {
    OK,
    INVALID,
    ERROR;

    companion object {
        fun of(status: String): ValueStatus = when(status) {
            "ok" -> OK
            "invalid" -> INVALID
            else -> ERROR
        }
    }
}