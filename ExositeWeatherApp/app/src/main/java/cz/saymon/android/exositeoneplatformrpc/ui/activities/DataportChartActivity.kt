package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.recyclerviewwithsection.DataportSectionHeader
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dataports_list.*
import timber.log.Timber
import javax.inject.Inject

class DataportChartActivity : AppCompatActivity() {

    object INTENT_EXTRA_KEY {
        val ALIAS = "cz.saymon.android.alias"
        val LOCATION = "cz.saymon.android.location"
        val VALUE_UNIT = "cz.saymon.android.value_unit"
    }

    @Inject
    lateinit var api: ServerApi
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataport_chart)
        appComponent.inject(this)

        callApi()
    }

    private fun handleResponse(dataset: List<Dataport>) {
        Timber.d(dataset.toString())
        // TODO: Set data to chart adapter #HERE#
        swiperefreshlayout.isRefreshing = false
    }

    private fun handleResponseError(throwable: Throwable) {
        Timber.d(throwable.toString())
        swiperefreshlayout.isRefreshing = false
        // TODO: Show Snackbar with error #HERE#
    }

    private fun callApi() {
        swiperefreshlayout.isRefreshing = true
        subscription?.dispose()

        subscription = api.callRpcApi(ServerRequest(Constants.ALIASES_PWM))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
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
