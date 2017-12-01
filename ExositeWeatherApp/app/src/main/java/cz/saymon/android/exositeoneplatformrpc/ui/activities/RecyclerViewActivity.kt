package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerRequest.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.utils.app
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_recycler_view.*
import timber.log.Timber

class RecyclerViewActivity : AppCompatActivity() {

    @Inject
    lateinit var api: ServerApi

    internal var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        app.appComponent.inject(this)

        recyclerview.setOnClickListener { callApi() }
    }

    private fun handleResponse(dataset: List<Dataport>?) {
        toast("onNext: ${System.currentTimeMillis()}")
        Timber.d(dataset.toString())
    }

    private fun handleResponse(throwable: Throwable?) {
        toast("onException: ${System.currentTimeMillis()}")
        Timber.d(throwable.toString())
    }

    private fun callApi() {
        subscription?.dispose()

        subscription = api.getItem(ServerRequest(Constants.ALIASES))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .toSortedList { dataport1, dataport2 -> dataport1.location.compareTo(dataport2.location) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponse(throwable) })
    }

}
