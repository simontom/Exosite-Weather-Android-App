package cz.saymon.android.exositeoneplatformrpc.dependency_injection.components

import cz.saymon.android.exositeoneplatformrpc.App
import cz.saymon.android.exositeoneplatformrpc.dependency_injection.modules.AppModule
import cz.saymon.android.exositeoneplatformrpc.dependency_injection.modules.NetworkModule
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpc.ui.activities.MainActivity
import cz.saymon.android.exositeoneplatformrpc.ui.activities.RecyclerViewActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
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

}
