package cz.saymon.android.exositeoneplatformrpc.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import kotlinx.android.synthetic.main.dataport_value_row.view.*

class DataportValueViewHolder(view: View, val clickListener: ((Dataport) -> Unit) = {})
    : RecyclerView.ViewHolder(view) {

    fun bind(dataport: Dataport) {
        val sectionText = "${dataport.values[0].value} ${dataport.type.unit}"

        with (itemView) {
            value.text = sectionText
            setOnClickListener { clickListener.invoke(dataport) }
        }
    }

}
