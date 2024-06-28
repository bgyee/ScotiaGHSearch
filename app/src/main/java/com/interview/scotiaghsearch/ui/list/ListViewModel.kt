package com.interview.scotiaghsearch.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.scotiaghsearch.data.model.UserInfo
import com.interview.scotiaghsearch.data.model.UserRepo
import com.interview.scotiaghsearch.data.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    private val _userRepoList = MutableLiveData<List<UserRepo>>()
    val userRepoList: LiveData<List<UserRepo>>
        get() = _userRepoList

    var totalForkCount: Int = 0


    fun getUserInfo(userId: String) {
        viewModelScope.launch {
            _userInfo.value = gitHubRepository.getUserInfo(userId)
        }
    }

    fun getRepoList(userId: String) {
        viewModelScope.launch {
            _userRepoList.value = gitHubRepository.getUserRepos(userId)

            var forkCount = 0
            _userRepoList.value?.forEach {
                forkCount += it.forks
            }

            totalForkCount = forkCount
        }
    }
}