package com.ydhnwb.android_refresh_token_example.data.login.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class GenerateAccessTokenResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiredAt: Date,
    @SerializedName("message") val message: String
    )