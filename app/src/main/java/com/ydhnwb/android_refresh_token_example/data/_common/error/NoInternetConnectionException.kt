package com.ydhnwb.android_refresh_token_example.data._common.error

import java.io.IOException

class NoInternetConnectionException : IOException() {
    override val message: String
        get() = "You are offline"
}