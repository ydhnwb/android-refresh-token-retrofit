package com.ydhnwb.android_refresh_token_example.data.login.repo

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ydhnwb.android_refresh_token_example.data._common.error.HttpCodeMessageMapper
import com.ydhnwb.android_refresh_token_example.data._common.model.RefreshTokenRequest
import com.ydhnwb.android_refresh_token_example.data._common.model.StdResponse
import com.ydhnwb.android_refresh_token_example.data._common.module.SharedPrefs
import com.ydhnwb.android_refresh_token_example.data.login.api.LoginApi
import com.ydhnwb.android_refresh_token_example.data.login.model.GenerateAccessTokenResponse
import com.ydhnwb.android_refresh_token_example.data.login.model.LoginRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import com.ydhnwb.android_refresh_token_example.domain.login.entity.LoginEntity
import com.ydhnwb.android_refresh_token_example.domain.login.repo.LoginRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepoImpl constructor(private val loginApi: LoginApi, private val pref: SharedPrefs) : LoginRepo {
    override suspend fun login(loginRequest: LoginRequest): Flow<Either<LoginEntity, Failure>> {
        return flow {
            val res = loginApi.login(loginRequest)
            if(res.isSuccessful){
                val b = res.body()!!
                val loginEntity = LoginEntity(
                    b.id,
                    b.name,
                    b.email,
                    b.accessToken,
                    b.accessTokenExpiredAt,
                    b.refreshToken,
                    b.refreshTokenExpiredAt
                )
                emit(Either.Success(loginEntity))
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

    override suspend fun generateNewAccessToken(refreshToken: String): Flow<Either<GenerateAccessTokenResponse, Failure>> {
        return flow {
            val body = RefreshTokenRequest(refreshToken)
            val res = loginApi.generateNewAccessToken(body)
            if (res.isSuccessful){
                val b = res.body()!!
                pref.updateAccessToken(b.accessToken, b.accessTokenExpiredAt)
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