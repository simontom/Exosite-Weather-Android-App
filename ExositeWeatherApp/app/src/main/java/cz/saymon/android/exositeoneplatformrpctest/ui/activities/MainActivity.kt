package cz.saymon.android.exositeoneplatformrpctest.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.saymon.android.exositeoneplatformrpctest.R
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerRequest.ServerRequest
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse.ServerResponse
import cz.saymon.android.exositeoneplatformrpctest.utils.app
import cz.saymon.android.exositeoneplatformrpctest.utils.contains
import cz.saymon.android.exositeoneplatformrpctest.utils.toast
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

    internal var subscription: Disposable? = null
    internal var serverRequest = ServerRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        app.appComponent.inject(this)

        main.setOnClickListener(this::testApi);

        Timber.d("onCreate()")

        toast("Initialized")

        regexContainsTest()
    }

    private fun regexContainsTest() {
        when("Simon") {
            in Regex(".+mon") -> println("ends with mon")
        }
    }

    private fun testApi(_ignored: View) {
        subscription?.dispose()

        subscription = api.getItem(serverRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponse(throwable) })
    }

    private fun handleResponse(dataset: List<ServerResponse>?) {
        toast("onNext: ${System.currentTimeMillis()}")
        Timber.d(dataset.toString())
        text.text = dataset.toString()

    }

    private fun handleResponse(throwable: Throwable?) {
        toast("onException: ${System.currentTimeMillis()}")
        text.text = throwable.toString()
    }

}
