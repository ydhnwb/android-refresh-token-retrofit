package com.ydhnwb.android_refresh_token_example.data.account.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)