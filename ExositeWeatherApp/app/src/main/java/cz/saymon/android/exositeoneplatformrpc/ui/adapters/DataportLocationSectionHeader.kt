package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import com.intrusoft.sectionedrecyclerview.Section
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport

data class DataportLocationSectionHeader(val nameOfSection: String, val children: List<Dataport>) : Section<Dataport> {
    override fun getChildItems(): List<Dataport> = children
}
