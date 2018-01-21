package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.SerializedName

enum class CallProcedureType(val procedureName: String) {

    @SerializedName("read")
    READ("read"),

    @SerializedName("write")
    WRITE("write")

}
