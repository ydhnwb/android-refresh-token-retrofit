package com.ydhnwb.android_refresh_token_example.data.account.repo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ydhnwb.android_refresh_token_example.data._common.error.HttpCodeMessageMapper
import com.ydhnwb.android_refresh_token_example.data._common.model.StdResponse
import com.ydhnwb.android_refresh_token_example.data.account.api.UserApi
import com.ydhnwb.android_refresh_token_example.domain.account.entity.UserEntity
import com.ydhnwb.android_refresh_token_example.domain.account.repo.UserRepo
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val userApi: UserApi) : UserRepo {
    override suspend fun getMyProfile(): Flow<Either<UserEntity, Failure>> {
        return flow {
            val res = userApi.getMyProfile()
            if(res.isSuccessful){
                val b = res.body()!!
                val userEntity = UserEntity(b.id, b.name, b.email)
                emit(Either.Success(userEntity))
            }else{
                val type = object : TypeToken<StdResponse>(){}.type
                val errResponse : StdResponse? = Gson().fromJson(res.errorBody()!!.charStream(), type)
                errResponse?.let {
                    emit(Either.Error(Failure(it.code, it.message)))
                } ?: run {
                    val message = HttpCodeMessageMapper.map(res.code())
                    emit(Either.Error(Failure(res.code(), message)))
                }

            }
        }
    }
}