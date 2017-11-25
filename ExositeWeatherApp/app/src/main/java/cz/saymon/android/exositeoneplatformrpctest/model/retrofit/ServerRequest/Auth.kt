package cz.saymon.android.exositeoneplatformrpctest.model.retrofit.ServerRequest

import com.google.gson.annotations.SerializedName

import cz.saymon.android.exositeoneplatformrpctest.model.Constants

data class Auth (
    @SerializedName("cik") private val cik: String = Constants.CIK
)
