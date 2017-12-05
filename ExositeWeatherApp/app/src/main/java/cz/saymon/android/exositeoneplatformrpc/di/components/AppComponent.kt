package cz.saymon.android.exositeoneplatformrpc.di.components

import cz.saymon.android.exositeoneplatformrpc.App
import cz.saymon.android.exositeoneplatformrpc.di.modules.AppModule
import cz.saymon.android.exositeoneplatformrpc.di.modules.HttpClientModule
import cz.saymon.android.exositeoneplatformrpc.di.modules.JsonConverterModule
import cz.saymon.android.exositeoneplatformrpc.di.modules.NetworkModule
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.ui.activities.MainActivity
import cz.saymon.android.exositeoneplatformrpc.ui.activities.PwmControlActivity
import cz.saymon.android.exositeoneplatformrpc.ui.activities.RecyclerViewActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, JsonConverterModule::class, HttpClientModule::class))
interface AppComponent {

    // Downstream components need these exposed
    ////////////////////////////////////////////
    fun exposeApp(): App

    fun exposeRetrofit(): Retrofit
    fun exposeServerApi(): ServerApi

    // Injections
    //////////////
    fun inject(activity: MainActivity)
    fun inject(activity: RecyclerViewActivity)
    fun inject(activity: PwmControlActivity)

}
