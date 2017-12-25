package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Value
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Argument
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ArgumentSelectionType
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.SnackbarDisplayer
import cz.saymon.android.exositeoneplatformrpc.ui.views.ChartSettings
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_dataport_chart.*
import timber.log.Timber
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

        line_chart.data = lineData
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


//        subscription = api.callRpcApi(ServerRequest(argument = argument))
//                .flatMapIterable(Dataport.MAPPER)
//                .map { it.values }
//                .window(3L) // Sample Window
//                // Use flatMap to make List of Windowed Values
//                // List of Values Avg toList again
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { dataset -> handleResponse(dataset) },
//                        { throwable -> handleResponseError(throwable) })
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
