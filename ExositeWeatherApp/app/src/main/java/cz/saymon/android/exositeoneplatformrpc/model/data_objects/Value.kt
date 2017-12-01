package cz.saymon.android.exositeoneplatformrpctest.model.data_objects

import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse.ServerValue

data class Value(val time: Long, val value: Double) {
    companion object {
        fun parseFrom(serverValue: ServerValue) = Value(serverValue.time, serverValue.value)
    }
}