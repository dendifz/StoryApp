package com.dendi.storyapp.ui.auth.login

import androidx.lifecycle.*
import com.dendi.storyapp.data.source.local.datastore.DataStoreManager
import com.dendi.storyapp.data.source.remote.ApiResult
import com.dendi.storyapp.data.source.remote.auth.AuthBody
import com.dendi.storyapp.data.source.remote.auth.AuthRepository
import com.dendi.storyapp.data.source.remote.auth.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val authRepository: AuthRepository,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    val tokenUser: LiveData<String> = dataStoreManager.tokenUser.asLiveData()

    fun loginUser(authBody: AuthBody): LiveData<ApiResult<AuthResponse>> {
        val result = MutableLiveData<ApiResult<AuthResponse>>()
        viewModelScope.launch {
            authRepository.loginUser(authBody).collect {
                result.postValue(it)
            }
        }
        return result
    }


}