package cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerResponse

import com.google.gson.annotations.SerializedName

data class ServerResponse(
        @SerializedName("id") val id: String? = null,
        @SerializedName("status") val status: String? = null,
        @SerializedName("result") val values: List<ServerValue>? = null)
