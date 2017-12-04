package cz.saymon.android.exositeoneplatformrpc.di.modules

import cz.saymon.android.exositeoneplatformrpc.model.Constants.BASE_SERVER_URL
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

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
    internal fun provideRetrofit(okHttp: OkHttpClient, rxAdapter: RxJava2CallAdapterFactory, gson: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxAdapter)
                .baseUrl(BASE_SERVER_URL)
                .client(okHttp)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideServerApi(retrofit: Retrofit): ServerApi {
        return retrofit.create(ServerApi::class.java)
    }

}
