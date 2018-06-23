package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.SerializedName

/**
 * Sort by value time
 */
enum class ArgumentSortType {

    /**
     * Starting with older values
     */
    @SerializedName("asc")
    ASCENDING,

    /**
     * Starting with newer values
     */
    @SerializedName("desc")
    DESCENDING

}
