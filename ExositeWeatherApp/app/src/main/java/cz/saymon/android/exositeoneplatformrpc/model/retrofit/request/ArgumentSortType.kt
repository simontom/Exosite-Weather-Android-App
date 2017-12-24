package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

/**
 * Sort by value time
 */
enum class ArgumentSortType(val sortName: String) {

    /**
     * From older values
     */
    ASCENDING("asc"),

    /**
     * From newer values
     */
    DESCENDING("desc")

}
