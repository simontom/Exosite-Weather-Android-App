package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.SerializedName

/**
 * Sort by value time
 */
enum class ArgumentSortType {

    /**
     * From older values
     */
    @SerializedName("asc")
    ASCENDING,

    /**
     * From newer values
     */
    @SerializedName("desc")
    DESCENDING

}
