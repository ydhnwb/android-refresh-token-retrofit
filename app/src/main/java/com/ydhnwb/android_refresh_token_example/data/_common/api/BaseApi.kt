package com.ydhnwb.android_refresh_token_example.data._common.api

import com.ydhnwb.android_refresh_token_example.data.login.model.GenerateAccessTokenResponse
import retrofit2.Response
import retrofit2.http.POST

interface BaseApi {
    @POST("auth/generate-new-access-token")
    fun generateNewAccessToken(refreshToken: String) : Response<GenerateAccessTokenResponse>
}