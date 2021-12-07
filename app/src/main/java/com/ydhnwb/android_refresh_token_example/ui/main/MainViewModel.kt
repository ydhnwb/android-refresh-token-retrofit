package com.ydhnwb.android_refresh_token_example.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ydhnwb.android_refresh_token_example.domain.account.usecase.GetMyProfileUseCase
import com.ydhnwb.android_refresh_token_example.domain.common.wrapper.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getMyProfileUseCase: GetMyProfileUseCase) : ViewModel(){
    private val _state = MutableLiveData<MainState>()
    val state : LiveData<MainState> get() = _state

    private fun setLoading(b: Boolean){
        _state.value = MainState.IsLoading(b)
    }

    private fun showToast(msg: String){
        _state.value = MainState.ShowToast(msg)
    }

    fun fetchProfile(){
        viewModelScope.launch {
            getMyProfileUseCase.execute().onStart {
                setLoading(true)
            }
                .catch { e ->
                    setLoading(false)
                    showToast(e.message.toString())
                }
                .collect {  res ->
                    setLoading(false)
                    when(res){
                        is Either.Success -> showToast("Fetch sukses. Access token masih ok")
                        is Either.Error -> showToast(res.err.message)
                    }
                }
        }
    }
}

sealed class MainState {
    data class IsLoading(val isLoading: Boolean): MainState()
    data class ShowToast(val message: String) : MainState()
}