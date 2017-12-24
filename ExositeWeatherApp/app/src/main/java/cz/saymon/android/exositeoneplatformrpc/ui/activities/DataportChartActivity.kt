package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Argument
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ArgumentSelectionType
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.SnackbarDisplayer
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_dataport_chart.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DataportChartActivity : AppCompatActivity(), SnackbarDisplayer {

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
    private val numberOfValuesPerHour = 3L

    @Inject
    lateinit var api: ServerApi
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataport_chart)
        appComponent.inject(this)

        val (alias, location, valueUnit) = getDataFrom(intent)
        setToolbarTitle("$location [$valueUnit]")

        callApi(alias)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun snackbarCoordinatorLayout() = main_layout

    private fun handleResponse(dataport: Dataport) {
        Timber.d(dataport.toString())
        // TODO: Set data to chart adapter #HERE#
        textview.text = "${dataport.values.size}\n$dataport"
    }

    private fun handleResponse(dates: List<Date>) {
        Timber.d(dates.toString())
        val text = dates.fold(StringBuilder(), { acc, date -> acc.append(date.toString()).append("\n") }).toString()
        textview.text = dates.size.toString() + "\n" + text
    }

    private fun handleResponseError(throwable: Throwable) {
        Timber.d(throwable.toString())
        showSnackbarError(R.string.message_error_no_internet)
    }

    private fun callApi(alias: String) {
        subscription?.dispose()

        val now = Date().time / 1000L
        val threeDaysAgo = now - (timeWindowHours * 60L * 60L)
        val maxNumberOfValues = timeWindowHours * numberOfValuesPerHour
        val argument = Argument(alias, starttime = threeDaysAgo, endtime = now,
                selection = ArgumentSelectionType.AUTOWINDOW, limit = maxNumberOfValues.toInt())

        subscription = api.callRpcApi(ServerRequest(argument = argument))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .flatMapIterable { it.values }
                .map { Date(it.timeMs) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponseError(throwable) })
    }


    override fun onDestroy() {
        subscription?.dispose()
        super.onDestroy()
    }

}
