package cz.saymon.android.exositeoneplatformrpc.model.retrofit

import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

// RPC description: http://docs.exosite.com/portals/rpc/
interface ServerApi {
    @POST("process")
    fun getItem(@Body request: ServerRequest = ServerRequest()): Flowable<List<ServerResponse>>
}
