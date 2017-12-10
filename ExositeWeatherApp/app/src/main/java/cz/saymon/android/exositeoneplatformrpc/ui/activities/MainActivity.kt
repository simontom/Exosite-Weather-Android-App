package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerResponse
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

// If we want to call the synthetic properties on View (useful in adapter classes)
//import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var api: ServerApi

    internal var subscription1: Disposable? = null
    internal var subscription2: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

        button1.setOnClickListener(this::testApi1)
        button2.setOnClickListener(this::testApi2)

        Timber.d("onCreate()")
        toast("Initialized")
    }

    private fun testApi1(_ignored: View) {
        subscription1?.dispose()

        subscription1 = api.callRpcApi()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse1(dataset) },
                        { throwable -> handleResponse(throwable) })
    }

    private fun testApi2(_ignored: View) {
        subscription2?.dispose()

        val dataports = listOf(
                Constants.ALIAS_TEMPBED, Constants.ALIAS_HUMBED,
                Constants.ALIAS_TEMPBAT, Constants.ALIAS_HUMBAT,
                Constants.ALIAS_TEMPLIV, Constants.ALIAS_HUMLIV)

        subscription2 = api.callRpcApi(ServerRequest(dataports))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .toSortedList { dataport1, dataport2 -> dataport1.location.compareTo(dataport2.location) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse2(dataset) },
                        { throwable -> handleResponse(throwable) })
    }

    private fun handleResponse1(dataset: List<ServerResponse>?) {
        toast("onNext: ${System.currentTimeMillis()}")
        Timber.d(dataset.toString())
        text.text = dataset.toString()
    }

    private fun handleResponse2(dataset: List<Dataport>?) {
        toast("onNext: ${System.currentTimeMillis()}")
        Timber.d(dataset.toString())
        text.text = dataset.toString()
    }

    private fun handleResponse(throwable: Throwable?) {
        toast("onException: ${System.currentTimeMillis()}")
        Timber.d(throwable.toString())
        text.text = throwable.toString()
    }

}
