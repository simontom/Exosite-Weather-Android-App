package cz.saymon.android.exositeoneplatformrpc.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.ServerResponse.ServerValue
import java.lang.reflect.Type
//import java.util.Date

class ValueGsonDeserializer : JsonDeserializer<ServerValue> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ServerValue {
        val valueJson = json.asJsonArray
        return ServerValue(valueJson.get(0).asLong, valueJson.get(1).asDouble)
//        return ServerValue(Date(valueJson.get(0).asLong), valueJson.get(1).asDouble)
    }

}
