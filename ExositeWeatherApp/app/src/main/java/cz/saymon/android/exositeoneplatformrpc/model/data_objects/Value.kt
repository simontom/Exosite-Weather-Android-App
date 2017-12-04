package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.model.retrofit.Response.ServerValue

data class Value(val time: Long, val value: Double) {
    companion object {
        fun parseFrom(serverValue: ServerValue) = Value(serverValue.time, serverValue.value)
    }
}