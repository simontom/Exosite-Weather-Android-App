package cz.saymon.android.exositeoneplatformrpctest.dependency_injection.modules

import cz.saymon.android.exositeoneplatformrpctest.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    internal fun provideApp() = app

}
