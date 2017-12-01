package cz.saymon.android.exositeoneplatformrpc.model.retrofit

import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerRequest.ServerRequest
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerResponse.ServerResponse
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerApi {
    @POST("process")
    fun getItem(@Body request: ServerRequest = ServerRequest()): Flowable<List<ServerResponse>>
}
