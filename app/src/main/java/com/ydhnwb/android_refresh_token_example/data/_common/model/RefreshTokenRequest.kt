package com.ydhnwb.android_refresh_token_example.data._common.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refresh_token") val refreshToken: String
)