package cz.saymon.android.exositeoneplatformrpc.model.retrofit.gsonutils

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Argument
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.Call
import cz.saymon.android.exositeoneplatformrpc.model.retrofit.request.CallProcedureType.*
import java.lang.reflect.Type


class CallSerializer : JsonSerializer<Call> {

    override fun serialize(src: Call, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        val callJsonObject = JsonObject()

        addIdProperty(callJsonObject, src)
        addProcedure(callJsonObject, src)

        when (src.procedure) {
            READ -> addArgumentsRead(callJsonObject, context, src)
            WRITE -> addArgumentsWrite(callJsonObject, context, src)
        }

        return callJsonObject
    }

    private fun addIdProperty(callJsonObject: JsonObject, src: Call) {
        val idSerializedName = "id"
        callJsonObject.addProperty(idSerializedName, src.id)
    }

    private fun addProcedure(callJsonObject: JsonObject, src: Call) {
        val procedureSerializedName = "procedure"
        callJsonObject.addProperty(procedureSerializedName, src.procedure.procedureName)
    }

    private fun createArgumentWithAlias(context: JsonSerializationContext, src: Call): JsonElement {
        val argumentWithId = Argument.createWithAlias(src.id)
        val argumentWithIdJson = context.serialize(argumentWithId)
        return argumentWithIdJson
    }

    private fun addArgumentsJsonArray(callJsonObject: JsonObject, src: Call, vararg argumentsJson: JsonElement) {
        val argumentsJsonArray = JsonArray()
        for (value in argumentsJson) {
            argumentsJsonArray.add(value)
        }

        val argumentsSerializedName = src.javaClass.getDeclaredField("arguments").getAnnotation(SerializedName::class.java).value
        callJsonObject.add(argumentsSerializedName, argumentsJsonArray)
    }

    private fun addArgumentsRead(callJsonObject: JsonObject, context: JsonSerializationContext, src: Call) {
        val argumentWithIdJson = createArgumentWithAlias(context, src)

        val argumentWithOptions = src.arguments.copy(alias = null)
        val argumentWithOptionsJson = context.serialize(argumentWithOptions)

        addArgumentsJsonArray(callJsonObject, src, argumentWithIdJson, argumentWithOptionsJson)
    }

    private fun addArgumentsWrite(callJsonObject: JsonObject, context: JsonSerializationContext, src: Call) {
        val argumentWithIdJson = createArgumentWithAlias(context, src)
        val argumentValueJson = context.serialize(src.arguments.writeValue)
        
        addArgumentsJsonArray(callJsonObject, src, argumentWithIdJson, argumentValueJson)
    }

}
