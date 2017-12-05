package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerValue

data class Value(val time: Long, val value: Double) {
    companion object {
        // Multiply by 1000 because Date takes milliseconds
        fun parseFrom(serverValue: ServerValue) = Value(serverValue.time * 1000, serverValue.value)
    }
}