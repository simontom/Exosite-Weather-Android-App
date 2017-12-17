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
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportLocation
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.SnackbarDisplayer
import cz.saymon.android.exositeoneplatformrpc.ui.adapters.DataportRecyclerViewAdapter_test
import cz.saymon.android.exositeoneplatformrpc.ui.adapters.DataportSectionHeader
import cz.saymon.android.exositeoneplatformrpc.utils.activityAs
import cz.saymon.android.exositeoneplatformrpc.utils.appComponent
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dataports_list.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class DataportsListFragment : Fragment() {

    @Inject
    lateinit var api: ServerApi
    private lateinit var adapter: DataportRecyclerViewAdapter_test
    private var subscription: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dataports_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)

        initRecyclerView()
        initSwipeRefreshLayout()
        callApi()
    }

    private fun initSwipeRefreshLayout() {
        swiperefreshlayout.setOnRefreshListener { callApi() }
        swiperefreshlayout.setColorSchemeResources(
                R.color.material_blue600, R.color.material_red600, R.color.material_deeporange600,
                R.color.material_pink600, R.color.material_yellow600,
                R.color.material_black, R.color.material_cyan600)
    }

    private fun initRecyclerView() {
        adapter = DataportRecyclerViewAdapter_test { toast("Clicked on: ${it.id}") }
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter
    }

    private fun handleResponse(dataset: TreeMap<DataportLocation, out List<Dataport>>) {
        Timber.d(dataset.toString())
        val sections = dataset.map {
            val dataportUpdateTime = it.value[0].values[0].time
            DataportSectionHeader(it.key, dataportUpdateTime, it.value)
        }
        adapter.notifyDataChanged(sections)
        swiperefreshlayout.isRefreshing = false
    }

    private fun handleResponse(throwable: Throwable) {
        Timber.d(throwable.toString())
        swiperefreshlayout.isRefreshing = false

        activityAs<SnackbarDisplayer>()?.showSnackbarError(R.string.message_error_no_internet)
    }

    private fun callApi() {
        swiperefreshlayout.isRefreshing = true
        subscription?.dispose()

        subscription = api.callRpcApi(ServerRequest(Constants.ALIASES))
                .delay(800, TimeUnit.MILLISECONDS)
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .groupBy { it.location }
                .flatMap {
                    it
                            .reduce(ArrayList<Dataport>(), { acc, dataport ->
                                acc.add(dataport)
                                acc
                            })
                            .doOnSuccess { it.sort() }
                            .toFlowable()
                }
                .toMap { it[0].location }
                .map { TreeMap(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponse(throwable) })
    }

}
