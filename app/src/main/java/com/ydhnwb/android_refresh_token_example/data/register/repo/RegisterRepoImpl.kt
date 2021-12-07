package com.ydhnwb.android_refresh_token_example.data.register.repo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ydhnwb.android_refresh_token_example.data._common.error.HttpCodeMessageMapper
import com.ydhnwb.android_refresh_token_example.data._common.model.StdResponse
import com.ydhnwb.android_refresh_token_example.data.register.api.RegisterApi
import com.ydhnwb.android_refresh_token_example.data.register.model.RegisterRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import com.ydhnwb.android_refresh_token_example.domain.register.repo.RegisterRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterRepoImpl constructor(private val registerApi: RegisterApi) : RegisterRepo {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Either<StdResponse, Failure>> {
        return flow {
            val res = registerApi.register(registerRequest)
            if(res.isSuccessful){
                val b = res.body()!!
                emit(Either.Success(b))
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