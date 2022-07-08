package com.dendi.storyapp.ui.addstory

import androidx.lifecycle.*
import com.dendi.storyapp.data.source.local.datastore.DataStoreManager
import com.dendi.storyapp.data.source.remote.ApiResult
import com.dendi.storyapp.data.source.remote.story.StoryRepository
import com.dendi.storyapp.data.source.remote.story.StoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddStoryVM @Inject constructor(
    private val storyRepository: StoryRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val tokenUser: LiveData<String> = dataStoreManager.tokenUser.asLiveData()

    fun addStory(
        token: String,
        desc: String,
        img: MultipartBody.Part
    ): LiveData<ApiResult<StoryResponse>> {
        val result = MutableLiveData<ApiResult<StoryResponse>>()
        viewModelScope.launch {
            storyRepository.addStory(token, desc, img).collect {
                result.postValue(it)
            }
        }
        return result
    }


}