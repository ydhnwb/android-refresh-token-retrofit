package com.ydhnwb.android_refresh_token_example.data._common.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.ydhnwb.android_refresh_token_example.data._common.error.NoInternetConnectionException
import com.ydhnwb.android_refresh_token_example.data._common.module.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class NetworkInterceptor constructor(private val applicationContext: Context, private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnected()){
            throw NoInternetConnectionException()
        }
        val user = pref.getUserData()
        val token = user?.accessToken ?: ""
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)

    }

    private fun isConnected() : Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                return if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    }
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        true
                    } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } else {
                    false
                }
            }else{
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                return nwInfo.isConnected
            }
        }
    }
}