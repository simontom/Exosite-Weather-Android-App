package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.recyclerview.DataportRecyclerViewAdapter
import cz.saymon.android.exositeoneplatformrpc.utils.app
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.operators.observable.ObservableDelay
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_recycler_view.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import android.util.TypedValue


class RecyclerViewActivity : AppCompatActivity() {

    @Inject
    lateinit var api: ServerApi
    private lateinit var adapter: DataportRecyclerViewAdapter
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        app.appComponent.inject(this)

        setRecyclerView()
        setSwipeRefreshLayout()

        callApi()
    }

    private fun setSwipeRefreshLayout() {
        swiperefreshlayout.setOnRefreshListener { callApi() }
        swiperefreshlayout.setColorSchemeResources(R.color.blue, R.color.red, R.color.orange,
                R.color.pink, R.color.yellow, R.color.black, R.color.cyan)
    }

    private fun setRecyclerView() {
        adapter = DataportRecyclerViewAdapter { toast("Clicked on: ${it.id}") }
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }

    private fun stopRefreshingLayout() {
        if (swiperefreshlayout.isRefreshing) {
            swiperefreshlayout.isRefreshing = false
        }
    }

    private fun handleResponse(dataset: List<Dataport>) {
        toast("onNext: ${System.currentTimeMillis()}")
        Timber.d(dataset.toString())
        adapter.setDataports(dataset)
        stopRefreshingLayout()
    }

    private fun handleResponse(throwable: Throwable) {
        toast("onException: ${System.currentTimeMillis()}")
        Timber.d(throwable.toString())
        stopRefreshingLayout()
    }

    private fun callApi() {
        swiperefreshlayout.isRefreshing = true
        subscription?.dispose()

//        subscription = Observable.just(true)
//                .delay(5000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { swiperefreshlayout.isRefreshing = false }

        subscription = api.getItem(ServerRequest(Constants.ALIASES))
                .delay(1500, TimeUnit.MILLISECONDS)
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .toSortedList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponse(throwable) })
    }

}
