package cz.saymon.android.exositeoneplatformrpctest.model.data_objects

import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse.ServerResponse
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse.ServerValue
import io.reactivex.functions.Function

data class Dataport(val id: String,
                    val status: DataportStatus,
                    val location: DataportLocation,
                    val type: DataportType,
                    val values: List<Value>) {
    companion object {
        val MAPPER: Function<in List<ServerResponse>, out List<Dataport>>? = Function { responses ->
            val dataports = ArrayList<Dataport>()

            for (response: ServerResponse in responses) {
                val id: String = response.id!!
                val status = DataportStatus.parseFrom(response.status!!)
                val location = DataportLocation.parseFrom(id)
                val type = DataportType.parseFrom(id)
                val values = (response.values ?: ArrayList<ServerResponse>()).map { Value.parseFrom(it as ServerValue) }
                dataports.add(Dataport(id, status, location, type, values))
            }

            return@Function dataports
        }

    }
}