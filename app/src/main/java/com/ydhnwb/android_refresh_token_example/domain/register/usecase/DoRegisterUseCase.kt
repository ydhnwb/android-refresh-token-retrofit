package com.ydhnwb.android_refresh_token_example.domain.register.usecase

import com.ydhnwb.android_refresh_token_example.data._common.model.StdResponse
import com.ydhnwb.android_refresh_token_example.data.register.model.RegisterRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import com.ydhnwb.android_refresh_token_example.domain.register.repo.RegisterRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoRegisterUseCase @Inject constructor(private val registerRepo: RegisterRepo){
    suspend fun execute(registerRequest: RegisterRequest) : Flow<Either<StdResponse, Failure>> {
        return registerRepo.register(registerRequest)
    }
}