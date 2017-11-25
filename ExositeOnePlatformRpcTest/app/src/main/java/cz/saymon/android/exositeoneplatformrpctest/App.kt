package cz.saymon.android.exositeoneplatformrpctest

import android.app.Application
import cz.saymon.android.exositeoneplatformrpctest.dependency_injection.components.AppComponent
import cz.saymon.android.exositeoneplatformrpctest.dependency_injection.components.DaggerAppComponent
import cz.saymon.android.exositeoneplatformrpctest.dependency_injection.modules.AppModule
import cz.saymon.android.exositeoneplatformrpctest.dependency_injection.modules.NetworkModule
import cz.saymon.android.exositeoneplatformrpctest.model.Constants.BASE_SERVER_URL
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(BASE_SERVER_URL))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        startTimberLogger()
        Timber.d("onCreate DONE")
    }

    private fun startTimberLogger() {
        Timber.plant(object : DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return super.createStackElementTag(element) + ':' + element.lineNumber
            }
        })
    }

}