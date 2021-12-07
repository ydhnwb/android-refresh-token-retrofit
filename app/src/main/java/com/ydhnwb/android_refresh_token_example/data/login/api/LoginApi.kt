package com.ydhnwb.android_refresh_token_example.data.login.api

import com.ydhnwb.android_refresh_token_example.data._common.model.RefreshTokenRequest
import com.ydhnwb.android_refresh_token_example.data.login.model.GenerateAccessTokenResponse
import com.ydhnwb.android_refresh_token_example.data.login.model.LoginRequest
import com.ydhnwb.android_refresh_token_example.data.login.model.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("auth/generate-new-access-token")
    suspend fun generateNewAccessToken(@Body refreshTokenRequest: RefreshTokenRequest) : Response<GenerateAccessTokenResponse>
}