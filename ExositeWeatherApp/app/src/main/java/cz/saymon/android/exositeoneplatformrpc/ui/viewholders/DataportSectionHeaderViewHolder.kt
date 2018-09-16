package cz.saymon.android.exositeoneplatformrpc.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportLocation
import cz.saymon.android.exositeoneplatformrpc.utils.toFormattedDate
import kotlinx.android.synthetic.main.dataport_section_row.view.*
import java.util.*

class DataportSectionHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(dataportLocation: DataportLocation, timeUpdated: Long) {
        with(itemView) {
            location.text = dataportLocation.locationName
            time.text = timeUpdated.toFormattedDate()

            val timeDiff = Date().time - timeUpdated
            isSelected = timeDiff > Constants.MAX_TIME_DIFFERENCE_MS
        }
    }

}
