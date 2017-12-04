package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

/**
 * Supports downsampling of the count of values
 * Note that these options provide a blind sampling function, not averaging or other type of rollup calculation
 */
enum class ArgumentSelectionType(val selectionName: String) {

    /**
     * Turns off downsampling, returning all points up to "limit"
     */
    ALL("all"),

    /**
     * Splits the time window evenly into "limit" parts and returns at most one point from each part
     */
    GIVENWINDOW("givenwindow"),

    /**
     * Samples evenly across points in the time window up to "limit"
     */
    AUTOWINDOW("autowindow")
}
