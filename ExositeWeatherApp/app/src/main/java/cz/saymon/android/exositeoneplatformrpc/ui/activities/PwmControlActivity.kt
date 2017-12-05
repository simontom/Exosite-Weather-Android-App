package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_pwm_control.*
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class PwmControlActivity : AppCompatActivity() {

    @Inject
    lateinit var api: ServerApi
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwm_control)

        appComponent.inject(this)

        readServerPwmValues()
    }

    private fun handleResponse(dataset: List<Dataport>) {
        toast("onNext: ${System.currentTimeMillis()} ms")
        Timber.d(dataset.toString())
    }

    private fun handleResponse(throwable: Throwable) {
        toast("onException: ${System.currentTimeMillis()} ms")
        Timber.d(throwable.toString())
    }

    private fun readServerPwmValues() {
        subscription?.dispose()

        subscription = api.getItem(ServerRequest(Constants.ALIASES_PWM))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponse(throwable) })
    }
}
