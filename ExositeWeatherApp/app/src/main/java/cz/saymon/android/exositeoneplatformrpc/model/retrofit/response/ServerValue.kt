package cz.saymon.android.exositeoneplatformrpc.model.retrofit.response

import com.google.gson.annotations.Expose

data class ServerValue(
        @Expose() val timeS: Long,
        @Expose() val value: Double)
