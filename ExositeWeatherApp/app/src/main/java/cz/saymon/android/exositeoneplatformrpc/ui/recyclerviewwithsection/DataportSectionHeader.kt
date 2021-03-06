package cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection

import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportLocation
import cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection.Section

data class DataportSectionHeader(
        val dataportLocation: DataportLocation,
        val dataportUpdateTime: Long,
        override val childItems: List<Dataport>
) : Section<Dataport>
