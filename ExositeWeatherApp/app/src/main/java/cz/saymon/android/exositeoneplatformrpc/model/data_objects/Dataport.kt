package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerResponse
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerValue
import io.reactivex.functions.Function

data class Dataport(val id: String,
                    val status: DataportStatus,
                    val location: DataportLocation,
                    val type: DataportType,
                    val values: List<Value>) : Comparable<Dataport> {

    override fun compareTo(other: Dataport): Int {
        var comparisionResult = location.compareTo(other.location)
        if (comparisionResult == 0) {
            comparisionResult = type.compareTo(other.type)
        }

        return comparisionResult
    }

    companion object {
        val MAPPER: Function<in List<ServerResponse>, out List<Dataport>> = Function { serverResponses ->
            val dataports = ArrayList<Dataport>()

            for (serverResponse: ServerResponse in serverResponses) {
                val id: String = serverResponse.id!!
                val status = DataportStatus.parseFrom(serverResponse.status!!)
                val location = DataportLocation.parseFrom(id)
                val type = DataportType.parseFrom(id)
                val values = (serverResponse.values ?: ArrayList<ServerResponse>()).map { Value.parseFrom(it as ServerValue) }
                dataports.add(Dataport(id, status, location, type, values))
            }

            return@Function dataports
        }
    }

}
