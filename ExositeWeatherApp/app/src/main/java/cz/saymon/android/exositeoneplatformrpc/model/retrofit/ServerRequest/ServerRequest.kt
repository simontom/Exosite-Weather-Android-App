package cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerRequest

import com.google.gson.annotations.SerializedName
import cz.saymon.android.exositeoneplatformrpctest.model.Constants
import java.util.ArrayList

data class ServerRequest(val dataportIds: List<String>?) {

    constructor() : this(null)

    @SerializedName("auth")
    val auth: Auth = Auth()
    @SerializedName("calls")
    var calls: List<Call> = Call.defaults
        private set

    init {
        if (dataportIds != null) {
            val calls = ArrayList<Call>()
            for (alias in dataportIds) {
                calls.add(Call(alias))
            }
            this.calls = calls
        }
    }

}