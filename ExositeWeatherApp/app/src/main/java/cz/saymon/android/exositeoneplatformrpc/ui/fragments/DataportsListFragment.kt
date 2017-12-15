package cz.saymon.android.exositeoneplatformrpc.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.adapters.DataportRecyclerViewAdapter
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dataports_list.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DataportsListFragment : Fragment() {

    @Inject
    lateinit var api: ServerApi
    private lateinit var adapter: DataportRecyclerViewAdapter
    private var subscription: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dataports_list, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)

//        appComponent.inject(this)

//        initRecyclerView()
//        initSwipeRefreshLayout()
//        callApi()
//    }

//    private fun initSwipeRefreshLayout() {
//        swiperefreshlayout.setOnRefreshListener { callApi() }
//        swiperefreshlayout.setColorSchemeResources(R.color.blue, R.color.red,
//                R.color.orange, R.color.pink, R.color.yellow, R.color.black, R.color.cyan)
//    }
//
//    private fun initRecyclerView() {
//        adapter = DataportRecyclerViewAdapter { toast("Clicked on: ${it.id}") }
//        recyclerview.layoutManager = LinearLayoutManager(context)
//        recyclerview.adapter = adapter
//    }
//
//    private fun handleResponse(dataset: List<Dataport>) {
//        toast("onNext: ${System.currentTimeMillis()} ms")
//        Timber.d(dataset.toString())
//        adapter.setDataports(dataset)
//        swiperefreshlayout.isRefreshing = false
//    }
//
//    private fun handleResponse(throwable: Throwable) {
//        toast("onException: ${System.currentTimeMillis()} ms")
//        Timber.d(throwable.toString())
//        swiperefreshlayout.isRefreshing = false
//    }
//
//    private fun callApi() {
//        swiperefreshlayout.isRefreshing = true
//        subscription?.dispose()
//
//        subscription = api.callRpcApi(ServerRequest(Constants.ALIASES))
//                .delay(800, TimeUnit.MILLISECONDS)
//                .flatMapIterable(Dataport.MAPPER)
//                .filter { it.status == DataportStatus.OK }
//                .toSortedList()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { dataset -> handleResponse(dataset) },
//                        { throwable -> handleResponse(throwable) })
//    }

}
