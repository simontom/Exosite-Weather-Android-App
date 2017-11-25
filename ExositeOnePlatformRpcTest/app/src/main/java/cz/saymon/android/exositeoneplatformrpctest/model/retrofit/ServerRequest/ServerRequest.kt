package cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerRequest

import com.google.gson.annotations.SerializedName

data class ServerRequest (
    @SerializedName("auth") val auth: Auth = Auth(),
    @SerializedName("calls") val calls: List<Call> = Call.defaults
)
