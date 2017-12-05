package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.util.Date
import kotlin.math.roundToLong

data class Argument(
        @SerializedName("alias") val alias: String? = null,
        @Expose(serialize = false) val writeValue: String? = null,
        @SerializedName("starttime") val starttime: Long? = 0,
        @SerializedName("endtime") val endtime: Long? = (Date().time / 1000.0).toLong(), // Because it returns milliseconds
        @SerializedName("sort") val sort: ArgumentSortType? = ArgumentSortType.DESCENDING,
        @SerializedName("limit") val limit: Int? = 1,
        @SerializedName("selection") val selection: ArgumentSelectionType? = ArgumentSelectionType.ALL
) {
    companion object {
        fun createWithAlias(alias: String) = Argument(alias, null, null, null,  null, null, null)
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