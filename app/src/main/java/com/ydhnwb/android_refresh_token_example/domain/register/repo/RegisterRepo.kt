package com.ydhnwb.android_refresh_token_example.domain.register.repo

import com.ydhnwb.android_refresh_token_example.data._common.model.StdResponse
import com.ydhnwb.android_refresh_token_example.data.register.model.RegisterRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import kotlinx.coroutines.flow.Flow

interface RegisterRepo {
    // i will use std response for simple. i dont have time to make it clean with entity. haha
    suspend fun register(registerRequest: RegisterRequest) : Flow<Either<StdResponse, Failure>>
}