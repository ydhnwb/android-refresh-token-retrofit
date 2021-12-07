package com.ydhnwb.android_refresh_token_example.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydhnwb.android_refresh_token_example.data.login.model.LoginRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.login.entity.LoginEntity
import com.ydhnwb.android_refresh_token_example.domain.login.usecase.DoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: DoLoginUseCase
) : ViewModel(){
    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> get() = _state

    private fun setLoading(b: Boolean){
        _state.value = LoginState.IsLoading(b)
    }

    private fun showToast(msg: String){
        _state.value = LoginState.ShowToast(msg)
    }

    private fun successLogin(loginEntity: LoginEntity){
        _state.value = LoginState.SuccessLogin(loginEntity)
    }

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            loginUseCase.execute(loginRequest)
                .onStart { setLoading(true) }
                .catch { e ->
                    setLoading(false)
                    showToast(e.message.toString())
                }
                .collect { res ->
                    setLoading(false)
                    when(res){
                        is Either.Success -> successLogin(res.data)
                        is Either.Error -> showToast(res.err.message)
                    }
                }
        }
    }
}

sealed class LoginState {
    data class IsLoading(val isLoading: Boolean) : LoginState()
    data class ShowToast(val message: String) : LoginState()
    data class SuccessLogin(val loginEntity: LoginEntity) : LoginState()
}