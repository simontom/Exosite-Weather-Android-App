package cz.saymon.android.exositeoneplatformrpc.model.data_objects

import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerResponse
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
                val status = DataportStatus.from(serverResponse.status!!)
                val location = DataportLocation.from(id)
                val type = DataportType.from(id)
                val values = (serverResponse.values ?: ArrayList())
                        .map { Value.from(it) }
                dataports.add(Dataport(id, status, location, type, values))
            }

            return@Function dataports
        }
    }

}
