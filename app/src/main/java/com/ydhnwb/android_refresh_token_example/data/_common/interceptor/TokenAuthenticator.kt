package com.ydhnwb.android_refresh_token_example.data._common.interceptor

import android.content.Context
import android.content.Intent
import android.util.Log
import com.ydhnwb.android_refresh_token_example.data._common.model.RefreshTokenRequest
import com.ydhnwb.android_refresh_token_example.data._common.module.SharedPrefs
import com.ydhnwb.android_refresh_token_example.data.login.api.LoginApi
import com.ydhnwb.android_refresh_token_example.data.login.model.GenerateAccessTokenResponse
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Failure
import com.ydhnwb.android_refresh_token_example.domain.login.repo.LoginRepo
import com.ydhnwb.android_refresh_token_example.ui._common.showToast
import com.ydhnwb.android_refresh_token_example.ui.login.LoginActivity
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import javax.inject.Inject

class TokenAuthenticator @Inject constructor (
    private val context: Context,
    private val pref: SharedPrefs,
    private val loginRepo: Lazy<LoginRepo>,
): Authenticator {
    //authenticate() akan diexecute jika kamu dapet response code 401 dr server.
    //di sini, kalau aku dapet 401 dr /login atau semacamnya, akan kureturn null (karena gak perlu request new token).
    //tapi kalau dr url lain, misal /profile, dapet 401, kemungkinan karena access token expired.
    //maka coba utk generate token baru lewat loginRepo.generateNewToken
    //kalau result dr generateNewToken bukan 200, berarti generate new tokennya ditolak (utk skrg ditolak karena refresh token tidak valid atau kedaluwarsa).
    //maka force logout.

    //jika dapet status code 200, maka update shared prefnya. lalu return kan chain requestnya, supaya..
    //request yg tadi dapet 401 (misal /profile), akan dicoba lagi. dengan token yg baru tentunya
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.i("TokenAuthenticator", "This is called")
        val user = pref.getUserData()
        if (user != null){

            Log.i("TokenAuthenticator", "Current token is ${user.accessToken}")
            Log.i("TokenAuthenticator", "Berusha mendapat akses token yang terbaru")
            var generatedToken: GenerateAccessTokenResponse? = null
            var code = 0
            runBlocking(Dispatchers.Main) { // <-- try to use IO or Default kalao bingung
                generateNewToken(user.refreshToken).collect { res ->
                    when(res){
                        is Either.Success -> {
                            generatedToken = res.data
                        }
                        is Either.Error -> {
                            code = res.err.code
                        }
                    }

                }
            }
            if (generatedToken != null){
                Log.i("TokenAuthenticator", "Yes!. Acess token berhasil didapet. value: ${user.accessTokenExpiredAt}. Kedaluwarsa [ada ${user.accessTokenExpiredAt}")
                // berhasil dapet access token terbaru. lanjutin request yg tadi sempet gagal.
                return response.request.newBuilder()
                    .header("Authorization", generatedToken!!.accessToken)
                    .build()
            }

            if (code != 200){
                Log.i("TokenAuthenticator", "Refresh token is also expired. Force logout")
                pref.clear()
                //this code below is smelly. kalo nemu cara yg lebih bagus, pake cara itu aja. =======
                context.startActivity(Intent(context, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                //=====================

                return null
            }

            return null
        }
        return null
    }

    private suspend fun generateNewToken(refreshToken: String): Flow<Either<GenerateAccessTokenResponse, Failure>> {
        return loginRepo.get().generateNewAccessToken(refreshToken)

    }
}