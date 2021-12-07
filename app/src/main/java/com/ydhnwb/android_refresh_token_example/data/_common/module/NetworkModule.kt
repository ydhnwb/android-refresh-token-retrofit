package com.ydhnwb.android_refresh_token_example.data._common.module

import android.content.Context
import com.ydhnwb.android_refresh_token_example.BuildConfig
import com.ydhnwb.android_refresh_token_example.data._common.interceptor.NetworkInterceptor
import com.ydhnwb.android_refresh_token_example.data._common.interceptor.TokenAuthenticator
import com.ydhnwb.android_refresh_token_example.data.login.api.LoginApi
import com.ydhnwb.android_refresh_token_example.domain.login.repo.LoginRepo
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BuildConfig.API_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(networkInterceptor: NetworkInterceptor, tokenAuthenticator: TokenAuthenticator) : OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(networkInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            authenticator(tokenAuthenticator)
        }.build()
    }

    @Provides
    fun provideInterceptor(@ApplicationContext context: Context, pref: SharedPrefs) : NetworkInterceptor {
        return NetworkInterceptor(context, pref)
    }

    @Provides
    fun provideAuthenticator(@ApplicationContext context: Context, pref: SharedPrefs, loginRepo: Lazy<LoginRepo>): TokenAuthenticator {
        return TokenAuthenticator(context, pref, loginRepo)
    }
}