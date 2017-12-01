package cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerRequest

import com.google.gson.annotations.SerializedName
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import java.util.*

data class Call(
        @SerializedName("id") val id: String,
        @SerializedName("procedure") val procedure: String = "read",
        @SerializedName("arguments") val arguments: MutableList<Argument> = ArrayList<Argument>()) {

    init {
        this.arguments.add(Argument(id))    // Adds alias
        this.arguments.add(Argument())      // Adds limit + sort
    }

    companion object {
        val defaults: List<Call> by lazy {
            val calls = ArrayList<Call>()

            calls.add(Call("errorAlias"))

            for (alias in Constants.ALIASES) {
                calls.add(Call(alias))
            }

            calls
        }
    }

}
