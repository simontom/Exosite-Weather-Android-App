package cz.saymon.android.exositeoneplatformrpc.model.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerResponse(
        @Expose() @SerializedName("id") val id: String? = null,
        @Expose() @SerializedName("status") val status: String? = null,
        @Expose() @SerializedName("result") val values: List<ServerValue>? = null)
