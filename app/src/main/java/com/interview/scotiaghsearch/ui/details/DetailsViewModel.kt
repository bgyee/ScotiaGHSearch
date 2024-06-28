package com.interview.scotiaghsearch.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.scotiaghsearch.data.model.UserRepo
import com.interview.scotiaghsearch.data.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _selectedRepo = MutableLiveData<UserRepo>()
    val selectedRepo: LiveData<UserRepo>
        get() = _selectedRepo

    fun selectRepo(repoName: String) {
        viewModelScope.launch {
            _selectedRepo.value = gitHubRepository.getUserRepo(repoName)
        }
    }
}