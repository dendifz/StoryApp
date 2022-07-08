package com.dendi.storyapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.storyapp.data.source.remote.ApiResult
import com.dendi.storyapp.data.source.remote.auth.AuthBody
import com.dendi.storyapp.data.source.remote.auth.AuthRepository
import com.dendi.storyapp.data.source.remote.auth.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun registerUser(authBody: AuthBody): LiveData<ApiResult<AuthResponse>> {
        val result = MutableLiveData<ApiResult<AuthResponse>>()
        viewModelScope.launch {
            authRepository.registerUser(authBody).collect {
                result.postValue(it)
            }
        }
        return result
    }

}