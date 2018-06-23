package cz.saymon.android.exositeoneplatformrpc.di.modules

import cz.saymon.android.exositeoneplatformrpc.App
import cz.saymon.android.exositeoneplatformrpc.utils.NetworkStatus
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class HttpClientModule {

    @Provides
    @Singleton
    internal fun provideOkHttpCache(app: App): Cache =
            Cache(File(app.cacheDir, CACHE_FOLDER_NAME), CACHE_SIZE_BYTES.toLong())

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, networkStatus: NetworkStatus): OkHttpClient {
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        val httpLoggingInterceptor = HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }

        val builderClient = OkHttpClient.Builder().apply {
            connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            cache(cache)
        }

        builderClient.addNetworkInterceptor { chain ->
            val isOnGoodConnection = networkStatus.isOnGoodConnection
            Timber.d("isOnGoodConnection: $isOnGoodConnection")

            val request = chain.request()
            val originalResponse = chain.proceed(request)

            builderClient.build()

            val maxAge = 60 * 60 // Read from cache
            val maxStale = 60 * 60 * 24 * 28 // Tolerate 4-weeks stale
            val cacheHeaderValue =
                    if (isOnGoodConnection)
                        "public, max-age=$maxAge"
                    else "public, only-if-cached, max-stale=$maxStale"

            return@addNetworkInterceptor originalResponse
                    .newBuilder()
                    .header("Cache-Control", cacheHeaderValue)
                    .build()
        }

        builderClient.addNetworkInterceptor { chain ->
            val cacheBuilder = CacheControl.Builder()
            cacheBuilder.maxAge(2, TimeUnit.MINUTES)
            cacheBuilder.build()
            val cacheControl = cacheBuilder.build()

            val response = chain.proceed(chain.request())
            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }

        builderClient.addInterceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build()
            chain.proceed(request)
        }

        builderClient.addInterceptor(httpLoggingInterceptor)
        return builderClient.build()
    }

    companion object {
        private val CACHE_FOLDER_NAME = "http-cache"
        private val CACHE_SIZE_BYTES = 10 * 1024 * 1024
        private val CONNECTION_TIMEOUT = 16
    }

}
