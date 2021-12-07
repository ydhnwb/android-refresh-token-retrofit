package com.ydhnwb.android_refresh_token_example.ui.register

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ydhnwb.android_refresh_token_example.R
import com.ydhnwb.android_refresh_token_example.data.register.model.RegisterRequest
import com.ydhnwb.android_refresh_token_example.databinding.ActivityRegisterBinding
import com.ydhnwb.android_refresh_token_example.ui._common.isEmail
import com.ydhnwb.android_refresh_token_example.ui._common.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val vm: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
        register()
    }

    private fun register(){
        binding.buttonRegister.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val pass = binding.editTextPassword.text.toString().trim()
            if(validate(name, email, pass)){
                vm.register(RegisterRequest(name, email, pass))
            }
        }
    }

    private fun observe(){
        vm.state.observe(this, { handleState(it) })
    }

    private fun handleState(state: RegisterState){
        when(state){
            is RegisterState.ShowToast -> showToast(state.message)
            is RegisterState.IsLoading -> {
                binding.buttonRegister.isEnabled = !state.isLoading
            }
            is RegisterState.SuccessRegister -> {
                showToast("Register berhasil. Silakan login")
                finish()
            }
        }
    }

    private fun validate(name: String, email: String, password: String) : Boolean{
        resetAllInputError()
        if(name.isEmpty()){
            setNameError(getString(R.string.error_name_not_valid))
            return false
        }

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
        setNameError(null)
        setEmailError(null)
        setPasswordError(null)
    }

    private fun setNameError(e: String?){
        binding.inputName.error = e
    }

    private fun setEmailError(e: String?){
        binding.inputEmail.error = e
    }

    private fun setPasswordError(e: String?){
        binding.inputPassword.error = e
    }
}