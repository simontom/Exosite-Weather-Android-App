package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerValue

data class Value(val timeMs: Long, val value: Double) : Comparable<Value> {

    override fun compareTo(other: Value): Int {
        return timeMs.compareTo(other.timeMs)
    }

    companion object {
        // Multiply by 1000 because Date expects [ms]
        fun from(serverValue: ServerValue) = Value(serverValue.timeS * 1000, serverValue.value)
    }
}
