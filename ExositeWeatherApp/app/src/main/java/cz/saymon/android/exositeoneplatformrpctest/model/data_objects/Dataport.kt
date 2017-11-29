package cz.saymon.android.exositeoneplatformrpctest.model.data_objects

data class Dataport(val id: String,
                    val status: DataportStatus,
                    val location: DataportLocation,
                    val type: DataportType,
                    val values: List<Value>)
