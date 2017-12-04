package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.annotations.SerializedName
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Argument
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Call
import java.lang.reflect.Type


class CallSerializer : JsonSerializer<Call> {

    override fun serialize(src: Call, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        val callJsonObject = JsonObject()

        addIdProperty(src, callJsonObject)
        addProcedure(src, callJsonObject)

        return callJsonObject
    }

    private fun addIdProperty(src: Call, callJsonObject: JsonObject) {
        val idSerializedName = src.javaClass.getField("id").getAnnotation(SerializedName::class.java).value
        callJsonObject.addProperty(idSerializedName, src.id)
    }

    private fun addProcedure(src: Call, callJsonObject: JsonObject) {
        val procedureSerializedName = src.javaClass.getField("procedure").getAnnotation(SerializedName::class.java).value
        callJsonObject.addProperty(procedureSerializedName, src.procedure.procedureName)
    }

    private fun addArguments(src: Call, context: JsonSerializationContext, callJsonObject: JsonObject) {
        val argumentId = Argument.createWithAlias(src.id)

    }

}
