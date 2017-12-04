package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ArgumentSortType
import java.lang.reflect.Type

class ArgumentSortTypeSerializer : JsonSerializer<ArgumentSortType> {
    override fun serialize(src: ArgumentSortType, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.sortName)
    }
}
