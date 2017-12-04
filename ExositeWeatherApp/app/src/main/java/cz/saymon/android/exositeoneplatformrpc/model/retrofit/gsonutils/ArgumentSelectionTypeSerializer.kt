package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.ArgumentSelectionType
import java.lang.reflect.Type

class ArgumentSelectionTypeSerializer : JsonSerializer<ArgumentSelectionType> {
    override fun serialize(src: ArgumentSelectionType, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.selectionName)
    }
}
