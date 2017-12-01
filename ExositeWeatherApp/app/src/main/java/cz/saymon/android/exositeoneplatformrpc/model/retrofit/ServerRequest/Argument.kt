package cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerRequest

import com.google.gson.annotations.SerializedName

data class Argument(
        @SerializedName("alias") val alias: String? = null,
        @SerializedName("limit") val limit: Int? = 1,
        @SerializedName("sort") val sort: String? = "desc"
) {
    constructor(_alias: String?) : this(alias = _alias, limit = null, sort = null)
}
