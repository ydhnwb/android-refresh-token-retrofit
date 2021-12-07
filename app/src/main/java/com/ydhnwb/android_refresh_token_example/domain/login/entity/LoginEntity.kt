package com.ydhnwb.android_refresh_token_example.domain.login.entity

import java.util.*

data class LoginEntity(
    val id: Int,
    val name: String,
    val email: String,
    val accessToken: String,
    val accessTokenExpiredAt: Date,
    val refreshToken: String,
    val refreshTokenExpiredAt: Date
)