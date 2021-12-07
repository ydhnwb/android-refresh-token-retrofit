package com.ydhnwb.android_refresh_token_example.domain.login.usecase

import com.ydhnwb.android_refresh_token_example.data.login.model.LoginRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import com.ydhnwb.android_refresh_token_example.domain.login.entity.LoginEntity
import com.ydhnwb.android_refresh_token_example.domain.login.repo.LoginRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoLoginUseCase @Inject constructor(private val loginRepo: LoginRepo){
    suspend fun execute(loginRequest: LoginRequest) : Flow<Either<LoginEntity, Failure>> {
        return loginRepo.login(loginRequest)
    }
}