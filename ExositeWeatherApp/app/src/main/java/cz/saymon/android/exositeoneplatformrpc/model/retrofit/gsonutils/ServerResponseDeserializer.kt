package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.*
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerResponse
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.response.ServerValue
import java.lang.reflect.Type

class ServerResponseDeserializer : JsonDeserializer<ServerResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ServerResponse {
        val jsonObject = json as JsonObject
        val resultJson = jsonObject["result"]

        val id = jsonObject["id"].asString
        val status = jsonObject["status"].asString
        val result = when (resultJson) {
            is JsonArray -> parseResultProperty(resultJson, context)
            is JsonPrimitive -> parseResultProperty(resultJson, context)
            null -> emptyList<ServerValue>()
            else -> throw JsonParseException("Expected JsonArray or JsonPrimitive, got: ${resultJson.javaClass}")
        }

        return ServerResponse(id, status, result)
    }

    private fun parseResultProperty(jsonArray: JsonArray, context: JsonDeserializationContext): List<ServerValue> {
        val listOfServerValues = jsonArray
                .map { context.deserialize<ServerValue>(it, ServerValue::class.java) }
                .toList()

        return listOfServerValues
    }

    private fun parseResultProperty(jsonPrimitive: JsonPrimitive, context: JsonDeserializationContext): List<ServerValue> {
        val serverValue = context.deserialize<ServerValue>(jsonPrimitive, ServerValue::class.java)
        return listOf(serverValue)
    }
}
