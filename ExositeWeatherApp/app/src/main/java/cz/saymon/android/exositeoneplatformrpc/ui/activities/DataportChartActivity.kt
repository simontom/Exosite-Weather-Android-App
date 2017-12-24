package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.EntryXComparator
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Value
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Argument
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ArgumentSelectionType
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.SnackbarDisplayer
import cz.saymon.android.exositeoneplatformrpc.ui.views.ChartMarkerView
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_dataport_chart.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DataportChartActivity : AppCompatActivity(), SnackbarDisplayer, OnChartValueSelectedListener {

    companion object {
        private val ALIAS = "cz.saymon.android.alias"
        private val LOCATION = "cz.saymon.android.location"
        private val VALUE_UNIT = "cz.saymon.android.value_unit"

        fun showActivity(activity: Context, alias: String, location: String, valueUnit: String) {
            val intent = Intent(activity, DataportChartActivity::class.java)
            with(intent) {
                putExtra(ALIAS, alias)
                putExtra(LOCATION, location)
                putExtra(VALUE_UNIT, valueUnit)
            }
            activity.startActivity(intent)
        }

        private fun getDataFrom(intent: Intent): List<String> {
            val alias = intent.getStringExtra(ALIAS)
            val location = intent.getStringExtra(LOCATION)
            val valueUnit = intent.getStringExtra(VALUE_UNIT)

            return listOf(alias, location, valueUnit)
        }
    }

    private object ChartSettings {
        private val timeWindowDays = 7L
        internal val timeWindowHours = timeWindowDays * 24L
        internal val maxNumberOfValuesPerHour = 4L
        @SuppressLint("SimpleDateFormat")
        private val xAxisDateFormatter = SimpleDateFormat("MM/dd HH:mm")
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
        private val lineDatasetMode = LineDataSet.Mode.LINEAR
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

            val timeFormatter = SimpleDateFormat("HH:mm")
            val chartMarkerView = ChartMarkerView(context, valueUnit, timeFormatter)
            chartMarkerView.chartView = line_chart // For bounds control
            line_chart.marker = chartMarkerView // Set the marker to the chart

            with(line_chart.xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                textSize = axisFontSize
                textColor = ContextCompat.getColor(context, axisFontColorRes)
                valueFormatter = IAxisValueFormatter { value, axis -> xAxisDateFormatter.format(value.toLong()) }
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

        internal fun createLineDataSet(context: Context, values: List<Value>): LineDataSet {
            val entries = values.map { Entry(it.timeMs.toFloat(), it.value.toFloat()) }
            // It is necessary to sort by x-values, otherwise, unexpected behaviour occurs
            Collections.sort(entries, EntryXComparator())

            val dataset = LineDataSet(entries, "_ignored_").apply {
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
            }

            return dataset
        }

        internal fun createLineData(context: Context, lineDataSet: LineDataSet): LineData {
            return LineData(lineDataSet).apply {
                setValueTextColor(ContextCompat.getColor(context, lineValueTextColorRes))
                setValueTextSize(lineValueTextSize)
            }
        }
    }

    @Inject
    lateinit var api: ServerApi
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataport_chart)
        appComponent.inject(this)

        val (alias, location, valueUnit) = getDataFrom(intent)
        supportActionBar?.title = "$location [$valueUnit]"

        ChartSettings.initializeChart(this, line_chart, valueUnit)

        callApi(alias)
    }

    override fun snackbarCoordinatorLayout(): CoordinatorLayout = main_layout

    private fun handleResponse(values: List<Value>) {
        Timber.d(values.toString())

        val lineDataSet = ChartSettings.createLineDataSet(this, values)
        val lineData = ChartSettings.createLineData(this, lineDataSet)

        line_chart.setData(lineData)
        line_chart.legend.isEnabled = false
        line_chart.invalidate()
    }

    private fun handleResponseError(throwable: Throwable) {
        Timber.d(throwable.toString())
        showSnackbarError(R.string.message_error_no_internet)
    }

    private fun callApi(alias: String) {
        subscription?.dispose()

        val now = Date().time / 1000L
        val threeDaysAgo = now - (ChartSettings.timeWindowHours * 60L * 60L)
        val maxNumberOfValues = ChartSettings.timeWindowHours * ChartSettings.maxNumberOfValuesPerHour
        val argument = Argument(alias, starttime = threeDaysAgo, endtime = now,
                selection = ArgumentSelectionType.AUTOWINDOW, limit = maxNumberOfValues.toInt())

        subscription = api.callRpcApi(ServerRequest(argument = argument))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .map { it.values }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponseError(throwable) })
    }

    override fun onDestroy() {
        subscription?.dispose()
        super.onDestroy()
    }

    override fun onNothingSelected() {
    }
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Timber.d("ValSelected: Value:${e?.y} xIndex:${e?.x} DataSetIndex:${h?.dataSetIndex}")
    }

}
