package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class ServerRequest(
        @Expose(serialize = false) val dataportIds: List<String>? = null,
        @Expose(serialize = false) val argument: Argument? = null) {

    @SerializedName("auth")
    val auth: Auth = Auth()

    @SerializedName("calls")
    var calls: MutableList<Call> = ArrayList()
        private set

    init {
        dataportIds?.let {
            for (alias in dataportIds) {
                calls.add(Call(alias))
            }
        } ?: argument?.let {
            val id = argument.alias!!
            val procedure = when (argument.writeValue == null) {
                true -> CallProcedureType.READ
                false -> CallProcedureType.WRITE
            }
            calls.add(Call(id, procedure, argument))
        } ?: calls.addAll(Call.defaultCalls)
    }

}
