package com.ydhnwb.android_refresh_token_example.domain.login.repo

import com.ydhnwb.android_refresh_token_example.data.login.model.GenerateAccessTokenResponse
import com.ydhnwb.android_refresh_token_example.data.login.model.LoginRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import com.ydhnwb.android_refresh_token_example.domain.login.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface LoginRepo {
    suspend fun login(loginRequest: LoginRequest) : Flow<Either<LoginEntity, Failure>>
    suspend fun generateNewAccessToken(refreshToken: String) : Flow<Either<GenerateAccessTokenResponse, Failure>>
}