package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.SerializedName
import java.util.*

data class ServerRequest(val dataportIds: List<String>?) {

    constructor() : this(null)

    @SerializedName("auth")
    val auth: Auth = Auth()

    @SerializedName("calls")
    var calls: List<Call> = Call.default
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
