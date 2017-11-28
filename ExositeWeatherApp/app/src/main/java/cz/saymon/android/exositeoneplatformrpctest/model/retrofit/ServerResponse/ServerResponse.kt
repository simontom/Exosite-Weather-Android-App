package cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse

import com.google.gson.annotations.SerializedName

data class ServerResponse(
        @SerializedName("id") private val id: String? = null,
        @SerializedName("status") private val status: String? = null,
        @SerializedName("result") private val values: List<ServerValue>? = null)
