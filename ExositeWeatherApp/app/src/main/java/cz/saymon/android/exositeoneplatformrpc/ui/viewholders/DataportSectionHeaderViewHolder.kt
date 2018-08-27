package cz.saymon.android.exositeoneplatformrpc.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportLocation
import cz.saymon.android.exositeoneplatformrpc.utils.colorAsDrawable
import cz.saymon.android.exositeoneplatformrpc.utils.toFormattedDate
import kotlinx.android.synthetic.main.dataport_section_row.view.*
import java.util.*

class DataportSectionHeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private val MAX_TIME_DIFFERENCE = 60L * 60L * 1000L
    }

    fun bind(dataportLocation: DataportLocation, timeUpdated: Long) {
        with(view) {
            location.text = dataportLocation.locationName
            time.text = timeUpdated.toFormattedDate()

            val timeDiff = Date().time - timeUpdated
            if (timeDiff > MAX_TIME_DIFFERENCE) {
                background = colorAsDrawable(R.color.oldDataBackground)
            }
        }
    }

}
