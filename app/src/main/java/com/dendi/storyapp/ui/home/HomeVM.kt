package com.dendi.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dendi.storyapp.data.source.local.datastore.DataStoreManager
import com.dendi.storyapp.data.source.remote.story.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val storyRepository: StoryRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun tokenUser(): LiveData<String> {
        return dataStoreManager.tokenUser.asLiveData()
    }

    fun pagingStory(token: String) = storyRepository.loadData("Bearer $token").cachedIn(viewModelScope)


    suspend fun getMapAll() = storyRepository.getData()

    fun removeSession() = viewModelScope.launch {
        dataStoreManager.deleteSession()
    }
}