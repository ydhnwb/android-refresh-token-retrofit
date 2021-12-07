package com.ydhnwb.android_refresh_token_example.data.account.api

import com.ydhnwb.android_refresh_token_example.data.account.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("user/profile")
    suspend fun getMyProfile() : Response<UserResponse>
}