package com.ydhnwb.android_refresh_token_example.data.login.module

import com.ydhnwb.android_refresh_token_example.data._common.module.SharedPrefs
import com.ydhnwb.android_refresh_token_example.data.login.api.LoginApi
import com.ydhnwb.android_refresh_token_example.data.login.repo.LoginRepoImpl
import com.ydhnwb.android_refresh_token_example.domain.login.repo.LoginRepo
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit) : LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepo(loginApi: LoginApi, pref: SharedPrefs) : LoginRepo {
        return LoginRepoImpl(loginApi, pref)
    }
}