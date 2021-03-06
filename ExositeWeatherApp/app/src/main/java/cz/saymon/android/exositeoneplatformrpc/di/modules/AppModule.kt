package cz.saymon.android.exositeoneplatformrpc.di.modules

import cz.saymon.android.exositeoneplatformrpc.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    internal fun provideApp() = app

}
