package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxSeekBar
import com.jakewharton.rxbinding2.widget.SeekBarProgressChangeEvent
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Argument
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerResponse
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_pwm_control.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PwmControlActivity : AppCompatActivity() {

    @Inject
    lateinit var api: ServerApi
    private var subscriptionRead: Disposable? = null
    private var subscriptionWrite: Disposable? = null
    private var subscriptionSeekBar: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwm_control)

        appComponent.inject(this)

        readServerPwmValues()
        initPwmSeekbars()
    }

    override fun onStop() {
        super.onStop()
        subscriptionSeekBar?.dispose()
    }

    private fun initPwmSeekbars() {
        RxSeekBar.changeEvents(pwmr)
                .ofType(SeekBarProgressChangeEvent::class.java)
                .toFlowable(BackpressureStrategy.LATEST)
                .debounce(Constants.UI_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { seekBarEvent ->
                    if (seekBarEvent.fromUser()) {
                        writeServerPwmValue(Constants.ALIAS_PWMR, seekBarEvent.progress())
                    }
                }
    }

    private fun handleResponse(dataset: List<Dataport>) {
        toast("onNext (READ): ${System.currentTimeMillis()} ms")
        Timber.d(dataset.toString())

        for (pwmDataport in dataset) {
            val progress = pwmDataport.values[0].value.toInt()
            when (pwmDataport.id) {
                Constants.ALIAS_PWMR -> pwmr.progress = progress
                Constants.ALIAS_PWMG -> pwmg.progress = progress
                Constants.ALIAS_PWMB -> pwmb.progress = progress
            }
        }
    }

    private fun handleResponseTest(dataset: List<ServerResponse>?) {
        toast("onNext (WRITE): ${System.currentTimeMillis()} ms")
        Timber.d(dataset.toString())
    }

    private fun handleResponse(throwable: Throwable) {
        toast("onException: ${System.currentTimeMillis()} ms")
        Timber.d(throwable.toString())
    }

    private fun writeServerPwmValue(pwmSeekBarAlias: String, progress: Int) {
        subscriptionWrite?.dispose()

        val argument = Argument.createWithAliasWriteValue(pwmSeekBarAlias, progress.toString())
        val serverRequest = ServerRequest(argument = argument)
        subscriptionWrite = api.callRpcApi(serverRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { responses: List<ServerResponse>? -> handleResponseTest(responses) },
                        { throwable -> handleResponse(throwable) }
                )
    }

    private fun readServerPwmValues() {
        subscriptionRead?.dispose()

        subscriptionRead = api.callRpcApi(ServerRequest(Constants.ALIASES_PWM))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponse(throwable) })
    }
}
