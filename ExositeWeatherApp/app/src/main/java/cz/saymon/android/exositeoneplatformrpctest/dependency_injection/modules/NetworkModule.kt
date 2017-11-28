package cz.saymon.android.exositeoneplatformrpctest.dependency_injection.modules

import com.google.gson.GsonBuilder
import cz.saymon.android.exositeoneplatformrpctest.App
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse.Value
import cz.saymon.android.exositeoneplatformrpctest.utils.ValueGsonDeserializer
import cz.saymon.android.exositeoneplatformrpctest.utils.NetworkStatus
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Suppress("unused")
@Module
class NetworkModule(private val mBaseServerUrl: String) {

    @Provides
    @Singleton
    internal fun provideOkHttpCache(app: App): Cache =
            Cache(File(app.cacheDir, CACHE_FOLDER_NAME), CACHE_SIZE_BYTES.toLong())

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, networkStatus: NetworkStatus): OkHttpClient {
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

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
            val cacheHeaderValue = when (isOnGoodConnection) {
                true -> "public, max-age=$maxAge"
                false -> "public, only-if-cached, max-stale=$maxStale"
            }

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
            val request = chain.request().newBuilder().header("Content-Type", "application/json; charset=utf-8").build()
            chain.proceed(request)
        }

        builderClient.addInterceptor(httpLoggingInterceptor)
        return builderClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        // Will run all the requests synchronously (on MainThread ?I guess?)
//        return RxJavaCallAdapterFactory.create();
        // Will run all the requests asynchronously -- ideal for Reactive approach
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @Singleton
    internal fun provideGson(): GsonConverterFactory {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Value::class.java, ValueGsonDeserializer())
        return GsonConverterFactory.create(builder.create())
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttp: OkHttpClient, rxAdapter: RxJava2CallAdapterFactory, gson: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxAdapter)
                .baseUrl(mBaseServerUrl)
                .client(okHttp)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideServerApi(retrofit: Retrofit): ServerApi {
        return retrofit.create(ServerApi::class.java)
    }

    companion object {
        private val CACHE_FOLDER_NAME = "http-cache"
        private val CACHE_SIZE_BYTES = 10 * 1024 * 1024
        private val CONNECTION_TIMEOUT = 16
    }

}
