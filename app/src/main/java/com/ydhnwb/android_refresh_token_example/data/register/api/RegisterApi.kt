package com.ydhnwb.android_refresh_token_example.data.register.api

import com.ydhnwb.android_refresh_token_example.data._common.model.StdResponse
import com.ydhnwb.android_refresh_token_example.data.register.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest) : Response<StdResponse>
}