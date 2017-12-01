package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.model.Constants
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.Dataport
import cz.saymon.android.exositeoneplatformrpc.model.data_objects.DataportStatus
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerRequest.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.ui.recyclerview.DataportRecyclerViewAdapter
import cz.saymon.android.exositeoneplatformrpc.utils.app
import cz.saymon.android.exositeoneplatformrpc.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_recycler_view.*
import timber.log.Timber

class RecyclerViewActivity : AppCompatActivity() {

    @Inject
    lateinit var api: ServerApi
    private lateinit var adapter: DataportRecyclerViewAdapter
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        app.appComponent.inject(this)

        button.setOnClickListener { callApi() }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = DataportRecyclerViewAdapter() { toast("Clicked on: ${it.id}") }
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }

    private fun handleResponse(dataset: List<Dataport>) {
        toast("onNext: ${System.currentTimeMillis()}")
        Timber.d(dataset.toString())
        adapter.setDataports(dataset)
    }

    private fun handleResponse(throwable: Throwable) {
        toast("onException: ${System.currentTimeMillis()}")
        Timber.d(throwable.toString())
    }

    private fun callApi() {
        subscription?.dispose()

        subscription = api.getItem(ServerRequest(Constants.ALIASES))
                .flatMapIterable(Dataport.MAPPER)
                .filter { it.status == DataportStatus.OK }
                .toSortedList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dataset -> handleResponse(dataset) },
                        { throwable -> handleResponse(throwable) })
    }

}
