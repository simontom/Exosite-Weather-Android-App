package cz.saymon.android.exositeoneplatformrpctest.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerResponse.Value
import java.lang.reflect.Type

class ValueGsonDeserializer : JsonDeserializer<Value> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Value {
        val valueJson = json.asJsonArray
        return Value(valueJson.get(0).asLong, valueJson.get(1).asDouble)
    }

}
