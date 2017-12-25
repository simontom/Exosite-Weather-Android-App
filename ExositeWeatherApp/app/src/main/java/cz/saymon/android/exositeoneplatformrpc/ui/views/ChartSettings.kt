package cz.saymon.android.exositeoneplatformrpc.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.EntryXComparator
import com.github.mikephil.charting.utils.Utils
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Value
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
internal object ChartSettings {
    private val xAxisDateTimeFormatter = SimpleDateFormat("MM/dd HH:mm")
    internal val chartMarkerValueDateFormatter = SimpleDateFormat("MM/dd")
    internal val chartMarkerValueTimeFormatter = SimpleDateFormat("HH:mm")

    private val timeWindowDays = 7L
    internal val timeWindowHours = timeWindowDays * 24L
    internal val maxNumberOfValuesPerHour = 8L
    private val axisFontSize = 12.0F
    private val dragDecelerationFrictionCoef = 0.9F
    private val backgroundColoroRes = R.color.material_grey100
    private val axisFontColorRes = R.color.material_pink900
    private val datasetLineColorRes = R.color.material_pink400
    private val lineDatasetCircleRadius = 2.5F
    private val lineDatasetCircleHoleRadius = 1.5F
    private val lineDatasetCircleColorRes = R.color.material_teal400
    private val lineDatasetCircleHoleColorRes = R.color.material_yellow600
    private val lineDatasetWidth = 2.0F
    private val lineDatasetMode = LineDataSet.Mode.HORIZONTAL_BEZIER
    private val lineValueTextSize = 10.0F
    private val lineValueTextColorRes = R.color.material_yellow900

    @SuppressLint("SimpleDateFormat")
    internal fun initializeChart(context: Context, line_chart: LineChart, valueUnit: String) {
        with(line_chart) {
            setOnChartValueSelectedListener(context as OnChartValueSelectedListener)
            description.isEnabled = false
            dragDecelerationFrictionCoef = ChartSettings.dragDecelerationFrictionCoef
            isDragEnabled = true
            isHighlightPerDragEnabled = true
            setPinchZoom(true)
            setScaleEnabled(true)
            setDrawGridBackground(true)
            setBackgroundColor(ContextCompat.getColor(context, backgroundColoroRes))
        }

        val chartMarkerView = ChartMarkerView(context, valueUnit)
        chartMarkerView.chartView = line_chart // For bounds control
        line_chart.marker = chartMarkerView // Set the marker to the chart

        with(line_chart.xAxis) {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = axisFontSize
            textColor = ContextCompat.getColor(context, axisFontColorRes)
            valueFormatter = IAxisValueFormatter { value, _ -> xAxisDateTimeFormatter.format(value.toLong()) }
        }

        with(line_chart.axisLeft) {
            setDrawGridLines(true)
            textSize = axisFontSize
            textColor = ContextCompat.getColor(context, axisFontColorRes)
        }

        with(line_chart.axisRight) {
            isEnabled = false
        }
    }

    internal fun createLineDataSet(context: Context, values: List<Value>, sortXaxisValues: Boolean = false): LineDataSet {
        val entries = values.map { Entry(it.timeMs.toFloat(), it.value.toFloat()) }
        // It is necessary to sort by x-values, otherwise, unexpected behaviour occurs
        if (sortXaxisValues) {
            Collections.sort(entries, EntryXComparator())
        }

        return LineDataSet(entries, "_ignored_")
                .apply {
                    axisDependency = YAxis.AxisDependency.LEFT
                    mode = lineDatasetMode
                    color = ContextCompat.getColor(context, datasetLineColorRes)
                    lineWidth = lineDatasetWidth
                    setDrawCircles(true)
                    setDrawValues(true)
                    highLightColor = ContextCompat.getColor(context, lineDatasetCircleColorRes)
                    setCircleColor(ContextCompat.getColor(context, lineDatasetCircleColorRes))
                    circleRadius = lineDatasetCircleRadius
                    setDrawCircleHole(true)
                    circleHoleRadius = lineDatasetCircleHoleRadius
                    setCircleColorHole(ContextCompat.getColor(context, lineDatasetCircleHoleColorRes))
                    setValueFormatter { value, _, _, _ ->
                        Utils.formatNumber(value, 1, true)
                    }
                }
    }

    internal fun createLineData(context: Context, lineDataSet: LineDataSet): LineData {
        return LineData(lineDataSet).apply {
            setValueTextColor(ContextCompat.getColor(context, lineValueTextColorRes))
            setValueTextSize(lineValueTextSize)
        }
    }

    internal fun setDataToChart(line_chart: LineChart, lineData: LineData) {
        line_chart.data = lineData
        line_chart.legend.isEnabled = false
        line_chart.invalidate()
    }
}
