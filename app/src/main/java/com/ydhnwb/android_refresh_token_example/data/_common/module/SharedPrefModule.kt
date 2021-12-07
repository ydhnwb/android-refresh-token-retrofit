package com.ydhnwb.android_refresh_token_example.data._common.module

import android.content.Context
import com.ydhnwb.android_refresh_token_example.data._common.module.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context) : SharedPrefs {
        return SharedPrefs(context)
    }
}