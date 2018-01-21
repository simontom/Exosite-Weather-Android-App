package cz.saymon.android.exositeoneplatformrpc.di.modules

import com.google.gson.GsonBuilder
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils.CallSerializer
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils.ServerResponseDeserializer
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils.ServerValueDeserializer
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Call
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerResponse
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerValue
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class JsonConverterModule {

    @Provides
    @Singleton
    internal fun provideGson(): GsonConverterFactory {
        val builder = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Call::class.java, CallSerializer())
                .registerTypeAdapter(ServerValue::class.java, ServerValueDeserializer())
                .registerTypeAdapter(ServerResponse::class.java, ServerResponseDeserializer())
        return GsonConverterFactory.create(builder.create())
    }

}
