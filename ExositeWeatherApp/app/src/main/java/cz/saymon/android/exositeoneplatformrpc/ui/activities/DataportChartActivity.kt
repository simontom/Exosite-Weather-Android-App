package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
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

    private val timeWindowHours = 72L
    private val maxNumberOfValuesPerHour = 4L

    @Inject
    lateinit var api: ServerApi
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataport_chart)
        appComponent.inject(this)

        val (alias, location, valueUnit) = getDataFrom(intent)
        supportActionBar?.title = "$location [$valueUnit]"

        initializeLineChart(valueUnit)

        callApi(alias)
    }

    @SuppressLint("SimpleDateFormat")
    private fun initializeLineChart(valueUnit: String) {
        with(line_chart) {
            setOnChartValueSelectedListener(this@DataportChartActivity)
            description.isEnabled = false
            setTouchEnabled(true)
            setDrawGridBackground(false)
            dragDecelerationFrictionCoef = 0.9f
            setScaleEnabled(true)
            isDragEnabled = true
            setPinchZoom(true)
            isHighlightPerDragEnabled = true
            setBackgroundColor(ContextCompat.getColor(this@DataportChartActivity, R.color.material_grey100))
        }

        val chartMarkerView = ChartMarkerView(this, valueUnit)
        chartMarkerView.chartView = line_chart // For bounds control
        line_chart.marker = chartMarkerView // Set the marker to the chart

        with(line_chart.xAxis) {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 12.0F
            textColor = ContextCompat.getColor(this@DataportChartActivity, R.color.material_pink900)
            val simpleDateFormatter = SimpleDateFormat("MM/dd HH:mm")
            valueFormatter = IAxisValueFormatter { value, axis -> simpleDateFormatter.format(value.toLong()) }
        }

        with(line_chart.axisLeft) {
            setDrawGridLines(true)
            textSize = 12.0F
            textColor = ContextCompat.getColor(this@DataportChartActivity, R.color.material_pink900)
        }

        with(line_chart.axisRight) {
            isEnabled = false
        }
    }

    override fun snackbarCoordinatorLayout() = main_layout

    private fun createChartDataset(values: List<Value>): LineDataSet {
        val entries = values.map { Entry(it.timeMs.toFloat(), it.value.toFloat()) }
        // It is necessary to sort by x-values, otherwise, unexpected behaviour occurs
        Collections.sort(entries, EntryXComparator())

        val dataset = with(LineDataSet(entries, "DataSet")) {
            axisDependency = YAxis.AxisDependency.LEFT
            color = ContextCompat.getColor(this@DataportChartActivity, R.color.colorAccent)
            valueTextColor = ContextCompat.getColor(this@DataportChartActivity, R.color.colorPrimaryDark)
            formLineWidth = 2.0F
            setDrawCircles(true)
            setDrawValues(true)
            highLightColor = ContextCompat.getColor(this@DataportChartActivity, R.color.material_teal400)
            setCircleColor(ContextCompat.getColor(this@DataportChartActivity, R.color.material_teal400))
            circleRadius = 2.5F
            setDrawCircleHole(true)
            circleHoleRadius = 1.5F
            setCircleColorHole(ContextCompat.getColor(this@DataportChartActivity, R.color.material_yellow600))
            mode = LineDataSet.Mode.LINEAR
            this@with
        }

        return dataset
    }

    private fun handleResponse(values: List<Value>) {
        Timber.d(values.toString())
        val dataset = createChartDataset(values)

        val lineData = LineData(dataset)
        lineData.setValueTextColor(ContextCompat.getColor(this@DataportChartActivity, R.color.material_yellow900))
        lineData.setValueTextSize(10.0F)

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
        val threeDaysAgo = now - (timeWindowHours * 60L * 60L)
        val maxNumberOfValues = timeWindowHours * maxNumberOfValuesPerHour
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
