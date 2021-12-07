package com.ydhnwb.android_refresh_token_example.data.account.module

import com.ydhnwb.android_refresh_token_example.data.account.api.UserApi
import com.ydhnwb.android_refresh_token_example.data.account.repo.UserRepoImpl
import com.ydhnwb.android_refresh_token_example.domain.account.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit) : UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepo(userApi: UserApi) : UserRepo {
        return UserRepoImpl(userApi)
    }

}