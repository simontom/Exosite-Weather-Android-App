package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.*
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerValue
import java.lang.reflect.Type

class ServerValueDeserializer : JsonDeserializer<ServerValue> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ServerValue {
        when (json) {
            is JsonArray -> return ServerValue(json.get(0).asLong, json.get(1).asDouble)
            is JsonPrimitive -> return ServerValue(json.asLong, 0.0)
            else -> throw JsonParseException("Expected JsonArray or JsonPrimitive, got: ${json.javaClass}")
        }
    }
}
