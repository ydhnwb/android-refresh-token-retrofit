package com.ydhnwb.android_refresh_token_example.data._common.model

import com.google.gson.annotations.SerializedName

data class StdResponse (
    @SerializedName("message") val message : String,
    @SerializedName("code") val code : Int
    )