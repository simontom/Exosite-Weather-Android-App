package cz.saymon.android.exositeoneplatformrpc.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class CacheInterceptor @Inject
constructor(private val networkStatus: NetworkStatus) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        /*Request.Builder builderReq = chain.request().newBuilder();
        builderReq.header("Content-Type", "application/json; charset=utf-8");
        if (App.isNetworkAvailable()) {
            builderReq.header("Cache-Control", "public, max-age=" + 120); // 120 secs
        }
        else {
            builderReq.header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7); // 7 days
        }
        return chain.proceed(builderReq.build());*/

        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()

        request.header("Content-Type", "application/json; charset=utf-8")
        //        if (originalRequest.header("fresh") != null) {
        //            request.cacheControl(CacheControl.FORCE_NETWORK);
        //        }

        val response = chain.proceed(request.build())
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Connection")
                .removeHeader("Accept-Encoding")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl())
                .build()
    }

    private fun cacheControl(): String {
        // Units in SECONDS
        val cacheHeaderValue: String
        if (networkStatus.isOnGoodConnection) {
            cacheHeaderValue = "public, max-age=2419200"
        } else {
            cacheHeaderValue = "public, only-if-cached, max-stale=2419200"
        }
        return cacheHeaderValue
    }

}
