package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportLocation
import cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection.Section

data class DataportSectionHeader(
        val dataportLocation: DataportLocation,
        val datportUpdateTime: Long,
        override val childItems: List<Dataport>
) : Section<Dataport>
