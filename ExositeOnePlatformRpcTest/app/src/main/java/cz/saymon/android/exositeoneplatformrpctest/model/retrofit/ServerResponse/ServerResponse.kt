package cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse

import com.google.gson.annotations.SerializedName

/**
 * Created by SaymonRey on 13.12.2016.
 */

class ServerResponse {

    @SerializedName("id")
    private val id: String? = null

    @SerializedName("status")
    private val status: String? = null

    @SerializedName("result")
    private val values: List<Value>? = null

}
