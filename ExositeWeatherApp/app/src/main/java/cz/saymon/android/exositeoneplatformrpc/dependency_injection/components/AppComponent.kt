package cz.saymon.android.exositeoneplatformrpctest.dependency_injection.components

import cz.saymon.android.exositeoneplatformrpctest.App
import cz.saymon.android.exositeoneplatformrpctest.dependency_injection.modules.AppModule
import cz.saymon.android.exositeoneplatformrpctest.dependency_injection.modules.NetworkModule
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerApi
import cz.saymon.android.exositeoneplatformrpctest.ui.activities.MainActivity
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

}
