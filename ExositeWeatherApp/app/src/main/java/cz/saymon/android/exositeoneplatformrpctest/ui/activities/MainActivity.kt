package cz.saymon.android.exositeoneplatformrpctest.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.saymon.android.exositeoneplatformrpctest.R
import cz.saymon.android.exositeoneplatformrpctest.model.Constants
import cz.saymon.android.exositeoneplatformrpctest.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpctest.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerRequest.ServerRequest
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse.ServerResponse
import cz.saymon.android.exositeoneplatformrpctest.utils.app
import cz.saymon.android.exositeoneplatformrpctest.utils.toast
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
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

        app.appComponent.inject(this)

        button1.setOnClickListener(this::testApi1)
        button2.setOnClickListener(this::testApi2)

        Timber.d("onCreate()")
        toast("Initialized")
    }

    private fun testApi1(_ignored: View) {
        subscription1?.dispose()

        subscription1 = api.getItem()
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

//        subscription2 = api.getItem(ServerRequest())
        subscription2 = api.getItem(ServerRequest(dataports))
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
