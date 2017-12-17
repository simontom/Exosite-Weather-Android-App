package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import android.view.ViewGroup
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection.SectionRecyclerViewAdapter
import cz.saymon.android.exositeoneplatformrpc.utils.inflate


class DataportRecyclerViewAdapter_test(val clickListener: ((Dataport) -> Unit)?)
    : SectionRecyclerViewAdapter<
        DataportSectionHeader,
        Dataport,
        DataportSectionHeaderViewHolder,
        DataportValueViewHolder>() {

    override fun onCreateSectionViewHolder(sectionViewGroup: ViewGroup, viewType: Int): DataportSectionHeaderViewHolder {
        val view = sectionViewGroup.inflate(R.layout.dataport_section_row)
        return DataportSectionHeaderViewHolder(view)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): DataportValueViewHolder {
        val view = childViewGroup.inflate(R.layout.dataport_value_row)
        return DataportValueViewHolder(view, clickListener)
    }

    override fun onBindSectionViewHolder(sectionViewHolder: DataportSectionHeaderViewHolder,
                                         sectionPosition: Int, section: DataportSectionHeader) {
        sectionViewHolder.bind(section.dataportLocation, section.datportUpdateTime)
    }

    override fun onBindChildViewHolder(childViewHolder: DataportValueViewHolder,
                                       sectionPosition: Int, childPosition: Int, child: Dataport) {
        childViewHolder.bind(child)
    }
    
}
