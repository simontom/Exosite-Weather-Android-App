package cz.saymon.android.exositeoneplatformrpc.model.retrofit.response

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
//import java.util.Date

class ServerValueGsonDeserializer : JsonDeserializer<ServerValue> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ServerValue {
        val valueJson = json.asJsonArray
        return ServerValue(valueJson.get(0).asLong, valueJson.get(1).asDouble)
//        return ServerValue(Date(valueJson.get(0).asLong), valueJson.get(1).asDouble)
    }

}
