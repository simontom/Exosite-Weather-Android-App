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
import io.reactivex.Flowable
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
    private var subscriptionWriteR: Disposable? = null
    private var subscriptionWriteG: Disposable? = null
    private var subscriptionWriteB: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwm_control)

        appComponent.inject(this)

        readServerPwmValues()
        initPwmSeekbars()
    }

    private fun initPwmSeekbars() {
        val flowablePwmR = RxSeekBar.changeEvents(pwmr)
                .ofType(SeekBarProgressChangeEvent::class.java)
                .toFlowable(BackpressureStrategy.LATEST)
                .debounce(Constants.UI_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)

        val flowablePwmG = RxSeekBar.changeEvents(pwmg)
                .ofType(SeekBarProgressChangeEvent::class.java)
                .toFlowable(BackpressureStrategy.LATEST)
                .debounce(Constants.UI_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)

        val flowablePwmB = RxSeekBar.changeEvents(pwmb)
                .ofType(SeekBarProgressChangeEvent::class.java)
                .toFlowable(BackpressureStrategy.LATEST)
                .debounce(Constants.UI_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)

        Flowable.merge(flowablePwmR, flowablePwmG, flowablePwmB)
                .filter { it.fromUser() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { seekBarEvent ->
                    writeServerPwmValueHelper(seekBarEvent.view().id, seekBarEvent.progress())
                }
    }

    private fun handleResponse(dataset: List<Dataport>) {
        toast("onNext (READ): ${System.currentTimeMillis()} ms")
        Timber.d(dataset.toString())

        for (pwmDataport in dataset) {
            val progress = pwmDataport.values[0].value.toInt()

            when (pwmDataport.id) {
                Constants.ALIAS_PWMR -> {
                    pwmr.progress = progress
                    pwmr_value.text = progress.toString()
                }
                Constants.ALIAS_PWMG -> {
                    pwmg.progress = progress
                    pwmg_value.text = progress.toString()
                }
                Constants.ALIAS_PWMB -> {
                    pwmb.progress = progress
                    pwmb_value.text = progress.toString()
                }
            }
        }
    }

    private fun handleResponseWrite(dataset: List<ServerResponse>?) {
        toast("onNext (WRITE): ${System.currentTimeMillis()} ms")
        Timber.d(dataset.toString())
    }

    private fun handleResponse(throwable: Throwable) {
        toast("onException: ${System.currentTimeMillis()} ms")
        Timber.d(throwable.toString())
    }

    private fun createWritePwmValueServerRequest(pwmSeekBarAlias: String, progress: Int): ServerRequest {
        val argument = Argument.createWithAliasWriteValue(pwmSeekBarAlias, progress.toString())
        val serverRequest = ServerRequest(argument = argument)
        return serverRequest
    }

    private fun callWriteApi(serverRequest: ServerRequest): Disposable {
        return api.callRpcApi(serverRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { responses: List<ServerResponse>? -> handleResponseWrite(responses) },
                        { throwable -> handleResponse(throwable) }
                )
    }

    private fun writeServerPwmRvalue(progress: Int) {
        pwmr_value.text = progress.toString()
        val serverRequest = createWritePwmValueServerRequest(Constants.ALIAS_PWMR, progress)
        subscriptionWriteR?.dispose()
        subscriptionWriteR = callWriteApi(serverRequest)
    }

    private fun writeServerPwmGvalue(progress: Int) {
        pwmg_value.text = progress.toString()
        val serverRequest = createWritePwmValueServerRequest(Constants.ALIAS_PWMG, progress)
        subscriptionWriteG?.dispose()
        subscriptionWriteG = callWriteApi(serverRequest)
    }

    private fun writeServerPwmBvalue(progress: Int) {
        pwmb_value.text = progress.toString()
        val serverRequest = createWritePwmValueServerRequest(Constants.ALIAS_PWMB, progress)
        subscriptionWriteB?.dispose()
        subscriptionWriteB = callWriteApi(serverRequest)
    }

    private fun writeServerPwmValueHelper(seekbarId: Int, seekbarProgress: Int) {
        when (seekbarId) {
            pwmr.id -> writeServerPwmRvalue(seekbarProgress)
            pwmg.id -> writeServerPwmGvalue(seekbarProgress)
            pwmb.id -> writeServerPwmBvalue(seekbarProgress)
        }
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
