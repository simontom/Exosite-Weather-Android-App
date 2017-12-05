package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class ServerRequest(@Expose(serialize = false) val dataportIds: List<String>? = null) {

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
