package com.ydhnwb.android_refresh_token_example.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydhnwb.android_refresh_token_example.data.register.model.RegisterRequest
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import com.ydhnwb.android_refresh_token_example.domain.register.usecase.DoRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: DoRegisterUseCase
) : ViewModel(){
    private val _state = MutableLiveData<RegisterState>()
    val state: LiveData<RegisterState> get() = _state

    private fun setLoading(b: Boolean){
        _state.value = RegisterState.IsLoading(b)
    }

    private fun successRegister(){
        _state.value = RegisterState.SuccessRegister
    }

    private fun showToast(msg: String){
        _state.value = RegisterState.ShowToast(msg)
    }

    fun register(registerRequest: RegisterRequest){
        viewModelScope.launch {
            registerUseCase.execute(registerRequest)
                .onStart { setLoading(true) }
                .catch { e ->
                    setLoading(false)
                    showToast(e.message.toString())
                }
                .collect { res ->
                    setLoading(false)
                    when(res){
                        is Either.Success -> successRegister()
                        is Either.Error -> showToast(res.err.message)
                    }
                }
        }
    }
}


sealed class RegisterState {
    data class IsLoading(val isLoading: Boolean) : RegisterState()
    object SuccessRegister : RegisterState()
    data class ShowToast(val message: String) : RegisterState()
}