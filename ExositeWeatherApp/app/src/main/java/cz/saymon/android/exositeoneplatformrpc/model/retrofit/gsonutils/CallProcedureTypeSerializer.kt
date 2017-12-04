package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.CallProcedureType
import java.lang.reflect.Type

class CallProcedureTypeSerializer : JsonSerializer<CallProcedureType> {
    override fun serialize(src: CallProcedureType, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.procedureName)
    }
}
