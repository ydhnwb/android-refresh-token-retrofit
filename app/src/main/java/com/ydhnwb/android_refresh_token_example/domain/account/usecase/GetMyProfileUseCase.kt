package com.ydhnwb.android_refresh_token_example.domain.account.usecase

import com.ydhnwb.android_refresh_token_example.domain.account.entity.UserEntity
import com.ydhnwb.android_refresh_token_example.domain.account.repo.UserRepo
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyProfileUseCase @Inject constructor(private val userRepo: UserRepo) {
    suspend fun execute() : Flow<Either<UserEntity, Failure>>{
        return userRepo.getMyProfile()
    }
}