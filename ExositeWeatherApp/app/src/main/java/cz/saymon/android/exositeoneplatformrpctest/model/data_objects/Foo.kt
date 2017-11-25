package cz.saymon.android.exositeoneplatformrpctest.model.data_objects

import com.google.gson.annotations.SerializedName

/**
 * Created by Simon on 22.11.2017.
 */
data class Foo(@SerializedName("ts") val text: String, val bar: Bar)