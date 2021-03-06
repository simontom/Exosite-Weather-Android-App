package cz.saymon.android.exositeoneplatformrpc

import android.app.Application
import cz.saymon.android.exositeoneplatformrpc.di.components.AppComponent
import cz.saymon.android.exositeoneplatformrpc.di.components.DaggerAppComponent
import cz.saymon.android.exositeoneplatformrpc.di.modules.AppModule
import cz.saymon.android.exositeoneplatformrpc.di.modules.NetworkModule
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
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