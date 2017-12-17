package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import kotlinx.android.synthetic.main.dataport_value_row.view.*

class DataportValueViewHolder(val view: View, val clickListener: ((Dataport) -> Unit)? = null)
    : RecyclerView.ViewHolder(view) {

    fun bind(dataport: Dataport) {
        val sectionText = "${dataport.values[0].value} ${dataport.type.unit}"

        view.value.setText(sectionText)
        clickListener?.let {
            view.setOnClickListener { it(dataport) }
        }
    }

}
