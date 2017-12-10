package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.utils.inflate
import java.util.ArrayList

class DataportRecyclerViewAdapter(val clickListener: (Dataport) -> Unit) : RecyclerView.Adapter<DataportViewHolder>() {

    val dataset: MutableList<Dataport> = ArrayList<Dataport>()

    fun setDataports(dataports: List<Dataport>) {
        with(dataset) {
            clear()
            addAll(dataports)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DataportViewHolder {
        val view = parent?.inflate(R.layout.dataport_item_row)
        val viewHolder = DataportViewHolder(view, clickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: DataportViewHolder?, position: Int) {
        holder?.bind(dataset[position])
    }

}
