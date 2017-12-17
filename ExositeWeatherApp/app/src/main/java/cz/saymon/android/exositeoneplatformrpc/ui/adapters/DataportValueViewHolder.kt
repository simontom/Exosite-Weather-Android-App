package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.utils.toFormattedDate
import kotlinx.android.synthetic.main.dataport_item_row.view.*

class DataportValueViewHolder(val view: View?, val clickListener: (Dataport) -> Unit) : RecyclerView.ViewHolder(view) {
//    lateinit var dataport: Dataport

    @SuppressLint("SetTextI18n")
    fun bind(dataport: Dataport) {
//        this.dataport = dataport
        view ?: return
        with (view) {
            location.text = "${dataport.location.locationName} - ${dataport.type.name}"
            value.text = "${dataport.values[0].value} ${dataport.type.unit}"
            time.text = dataport.values[0].time.toFormattedDate()
        }

        view.setOnClickListener { clickListener(dataport) }
    }
}
