package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Argument
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Call
import java.lang.reflect.Type


class CallSerializer : JsonSerializer<Call> {

    override fun serialize(src: Call, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        val callJsonObject = JsonObject()

        addIdProperty(callJsonObject, src)
        addProcedure(callJsonObject, src)
        addArguments(callJsonObject, context, src)

        return callJsonObject
    }

    private fun addIdProperty(callJsonObject: JsonObject, src: Call) {
        val idSerializedName = src.javaClass.getDeclaredField("id").getAnnotation(SerializedName::class.java).value
        callJsonObject.addProperty(idSerializedName, src.id)
    }

    private fun addProcedure(callJsonObject: JsonObject, src: Call) {
        val procedureSerializedName = src.javaClass.getDeclaredField("procedure").getAnnotation(SerializedName::class.java).value
        callJsonObject.addProperty(procedureSerializedName, src.procedure.procedureName)
    }

    private fun addArguments(callJsonObject: JsonObject, context: JsonSerializationContext, src: Call) {
        val argumentWithId = Argument.createWithAlias(src.id)
        val argumentWithIdJson = context.serialize(argumentWithId)

        val argumentWithOptions = src.arguments.copy(alias = null)
        val argumentWithOptionsJson = context.serialize(argumentWithOptions)

        val argumentsJsonArray = JsonArray()
        argumentsJsonArray.add(argumentWithIdJson)
        argumentsJsonArray.add(argumentWithOptionsJson)

        val argumentsSerializedName = src.javaClass.getDeclaredField("arguments").getAnnotation(SerializedName::class.java).value
        callJsonObject.add(argumentsSerializedName, argumentsJsonArray)
    }

}
