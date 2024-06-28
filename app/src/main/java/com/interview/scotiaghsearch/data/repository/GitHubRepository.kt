package com.interview.scotiaghsearch.data.repository

import android.util.Log
import com.interview.scotiaghsearch.data.model.UserInfo
import com.interview.scotiaghsearch.data.model.UserRepo
import com.interview.scotiaghsearch.data.network.GitHubApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepository @Inject constructor(
    private val gitHubApiService: GitHubApiService
) {
    private val TAG = GitHubRepository::class.simpleName

    private var repoList: List<UserRepo>? = null

    suspend fun getUserInfo(userId: String): UserInfo? {
        var userInfo: UserInfo? = null

        try {
            userInfo = gitHubApiService.getUserInfo(userId)
        } catch (exception: Exception) {
            Log.e(TAG, "getUserInfo($userId) failed", exception)
        }

        return userInfo
    }

    suspend fun getUserRepos(userId: String): List<UserRepo>? {
        try {
            repoList = gitHubApiService.getUserRepos(userId)
        } catch (exception: Exception) {
            Log.e(TAG, "getUserRepos($userId) failed", exception)
        }

        return repoList
    }

    fun getUserRepo(repoName: String): UserRepo? {
        repoList?.let {
            return it.first { repo -> repo.name == repoName }
        }

        return null
    }
}