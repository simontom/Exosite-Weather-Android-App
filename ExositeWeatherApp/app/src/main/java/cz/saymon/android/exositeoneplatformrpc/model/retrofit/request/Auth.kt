package cz.saymon.android.exositeoneplatformrpc.model.retrofit.request

import com.google.gson.annotations.SerializedName

import cz.saymon.android.exositeoneplatformrpc.model.Constants

data class Auth (
    @SerializedName("cik") private val cik: String = Constants.CIK
)
