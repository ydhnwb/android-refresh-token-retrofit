package com.ydhnwb.android_refresh_token_example.data.register.module

import com.ydhnwb.android_refresh_token_example.data.register.api.RegisterApi
import com.ydhnwb.android_refresh_token_example.data.register.repo.RegisterRepoImpl
import com.ydhnwb.android_refresh_token_example.domain.register.repo.RegisterRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {
    @Provides
    @Singleton
    fun provideRegisterApi(retrofit: Retrofit) : RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterRepo(registerApi: RegisterApi) : RegisterRepo {
        return RegisterRepoImpl(registerApi)
    }
}