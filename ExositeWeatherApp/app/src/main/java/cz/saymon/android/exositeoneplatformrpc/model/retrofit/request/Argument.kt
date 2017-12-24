package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Argument(
        @Expose() @SerializedName("alias") val alias: String? = null,
        @Expose(serialize = false) val writeValue: String? = null,
        @Expose() @SerializedName("starttime") val starttime: Long? = 0,
        @Expose() @SerializedName("endtime") val endtime: Long? = (Date().time / 1000), // Because it returns [ms]
        @Expose() @SerializedName("sort") val sort: ArgumentSortType? = ArgumentSortType.DESCENDING,
        @Expose() @SerializedName("limit") val limit: Int? = 1,
        @Expose() @SerializedName("selection") val selection: ArgumentSelectionType? = ArgumentSelectionType.ALL
) {
    companion object {
        fun createWithAlias(alias: String) =
                Argument(alias, null, null, null, null, null, null)

        fun createWithAliasWriteValue(alias: String, writeValue: String) =
                Argument(alias, writeValue, null, null, null, null, null)
    }
}

/*
"arguments": [
    <ResourceID>,
    {
        "starttime": number = 0
        "endtime": number = <current unix time>,
        "sort": "asc" | "desc" = "desc"
        "limit": number = 1,
        "selection": "all" | "givenwindow" | "autowindow" = "all",
    }
]

<ResourceID> / "alias" is the identifier of the device to read. See Identifying Resources for details.

"starttime" and "endtime" are Unix timestamps that specify the window of time to read.
    "starttime" defaults to 0 and "endtime" defaults to the current time.

"sort" defines the order in which points should be ordered: ascending ("asc") or
    descending ("desc") timestamp order. Defaults to "desc".

"limit" sets a maximum on the number of points to return. "limit" is applied after
    the results have been sorted, so different values of "sort" will return different
    sets of points. Defaults to 1.

"selection" supports downsampling. "givenwindow" splits the time window evenly into "limit" parts
    and returns at most one point from each part. "autowindow" samples evenly
    across points in the time window up to "limit". Note that these options provide a blind
    sampling function, not averaging or other type of rollup calculation. Defaults to "all",
    which turns off downsampling, returning all points up to "limit".
*/