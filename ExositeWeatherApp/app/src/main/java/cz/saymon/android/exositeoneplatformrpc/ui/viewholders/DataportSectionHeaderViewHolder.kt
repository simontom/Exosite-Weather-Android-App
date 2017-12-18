package cz.saymon.android.exositeoneplatformrpc.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportLocation
import cz.saymon.android.exositeoneplatformrpc.utils.toFormattedDate
import kotlinx.android.synthetic.main.dataport_section_row.view.*

class DataportSectionHeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(dataportLocation: DataportLocation, timeUpdated: Long) {
        with(view) {
            location.text = dataportLocation.locationName
            time.text = timeUpdated.toFormattedDate()
        }
    }

}
