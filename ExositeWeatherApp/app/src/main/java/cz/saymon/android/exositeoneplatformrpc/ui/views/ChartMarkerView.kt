package cz.saymon.android.exositeoneplatformrpc.ui.views

import android.annotation.SuppressLint
import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import cz.saymon.android.exositeoneplatformrpc.R
import kotlinx.android.synthetic.main.custom_chart_marker_view.view.*

@SuppressLint("ViewConstructor")
class ChartMarkerView(context: Context, val valueUnit: String)
    : MarkerView(context, R.layout.custom_chart_marker_view) {

    // Callbacks everytime the MarkerView is redrawn, can be used to update the content (user-interface)
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val formattedValue = Utils.formatNumber(e!!.y, 1, true) + valueUnit
        value.text = formattedValue

        val formattedDate = ChartSettings.chartMarkerValueDateFormatter.format(e.x)
        date.text = formattedDate

        val formattedTime = ChartSettings.chartMarkerValueTimeFormatter.format(e.x)
        time.text = formattedTime

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        val xOffset = (-(width / 8.8)).toFloat()
        val yOffset = (-height).toFloat()
        return MPPointF(xOffset,yOffset)
    }

}
