package com.ydhnwb.android_refresh_token_example.domain.account.repo

import com.ydhnwb.android_refresh_token_example.domain.account.entity.UserEntity
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun getMyProfile() : Flow<Either<UserEntity, Failure>>
}