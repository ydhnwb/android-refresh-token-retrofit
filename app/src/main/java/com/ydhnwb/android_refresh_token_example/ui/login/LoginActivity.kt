package com.ydhnwb.android_refresh_token_example.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ydhnwb.android_refresh_token_example.R
import com.ydhnwb.android_refresh_token_example.data._common.module.SharedPrefs
import com.ydhnwb.android_refresh_token_example.data.login.model.LoginRequest
import com.ydhnwb.android_refresh_token_example.databinding.ActivityLoginBinding
import com.ydhnwb.android_refresh_token_example.ui._common.isEmail
import com.ydhnwb.android_refresh_token_example.ui._common.showToast
import com.ydhnwb.android_refresh_token_example.ui.main.MainActivity
import com.ydhnwb.android_refresh_token_example.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val vm : LoginViewModel by viewModels()

    @Inject
    lateinit var pref: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
        doLogin()
        reg()
    }

    private fun observe(){
        vm.state.observe(this, { handleState(it) })
    }

    private fun handleState(state: LoginState){
        when(state){
            is LoginState.SuccessLogin -> {
                pref.saveUserDataAsJson(state.loginEntity)
                goToMainActivity()
            }
            is LoginState.IsLoading -> {
                binding.buttonLogin.isEnabled = !state.isLoading
            }
            is LoginState.ShowToast -> showToast(state.message)
        }
    }

    private fun goToMainActivity(){
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    private fun reg(){
        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun doLogin(){
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            if(validate(email, password)){
                vm.login(LoginRequest(email, password))
            }
        }
    }

    private fun validate(email: String, password: String) : Boolean{
        resetAllInputError()
        if(!email.isEmail()){
            setEmailError(getString(R.string.error_email_not_valid))
            return false
        }

        if(password.length < 8){
            setPasswordError(getString(R.string.error_password_not_valid))
            return false
        }

        return true
    }


    private fun resetAllInputError(){
        setEmailError(null)
        setPasswordError(null)
    }


    private fun setEmailError(e: String?){
        binding.inputEmail.error = e
    }

    private fun setPasswordError(e: String?){
        binding.inputPassword.error = e
    }
}