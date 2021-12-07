package com.ydhnwb.android_refresh_token_example.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.ydhnwb.android_refresh_token_example.data._common.module.SharedPrefs
import com.ydhnwb.android_refresh_token_example.databinding.ActivityMainBinding
import com.ydhnwb.android_refresh_token_example.ui._common.showToast
import com.ydhnwb.android_refresh_token_example.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var prefs: SharedPrefs

    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logout()
        showUserInfo()
        observe()
        fetchProfile()
        seeSharedPref()
    }

    private fun seeSharedPref() {
        binding.buttonView.setOnClickListener {
            showUserInfo()
        }
    }

    private fun fetchProfile(){
        binding.buttonFetchProfile.setOnClickListener {
            vm.fetchProfile()
        }
    }

    private fun observe(){
        vm.state.observe(this, { handleState(it) })
    }

    private fun handleState(state: MainState){
        when(state){
            is MainState.ShowToast -> showToast(state.message)
            is MainState.IsLoading -> {
                binding.buttonLogout.isEnabled = !state.isLoading
                binding.buttonFetchProfile.isEnabled = !state.isLoading
            }
        }
    }

    private fun showUserInfo(){
        val user = prefs.getUserData()
        user?.let {
            binding.textViewName.text = it.name
            binding.textViewEmail.text = it.email
            binding.textViewAccessTokenExpired.text = "Access token expired: ${it.accessTokenExpiredAt}"
            binding.textViewRefreshTokenExpired.text = "Ref Expired At: ${it.refreshTokenExpiredAt}"
        }
    }

    private fun logout(){
        binding.buttonLogout.setOnClickListener {
            prefs.clear()
            goToLoginActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        checkUser()
    }

    private fun goToLoginActivity(){
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }

    private fun checkUser(){
        val user = prefs.getUserData()
        if(user == null){
            goToLoginActivity()
        }
    }
}