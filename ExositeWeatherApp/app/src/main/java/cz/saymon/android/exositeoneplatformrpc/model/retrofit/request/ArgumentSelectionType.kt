package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.SerializedName

/**
 * Supports downsampling of the count of values
 * Note that these options provide a blind sampling function, not averaging or other type of rollup calculation
 */
enum class ArgumentSelectionType {

    /**
     * Turns off downsampling, returning all points up to "limit"
     */
    @SerializedName("all")
    ALL,

    /**
     * Splits the time window evenly into "limit" parts and returns at most one point from each part
     */
    @SerializedName("givenwindow")
    GIVENWINDOW,

    /**
     * Samples evenly across points in the time window up to "limit"
     */
    @SerializedName("autowindow")
    AUTOWINDOW

}
