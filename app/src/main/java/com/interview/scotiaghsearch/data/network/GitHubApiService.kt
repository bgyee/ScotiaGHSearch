package com.interview.scotiaghsearch.data.network

import com.interview.scotiaghsearch.data.model.UserInfo
import com.interview.scotiaghsearch.data.model.UserRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: String
    ): UserInfo

    @GET("users/{userId}/repos")
    suspend fun getUserRepos(
        @Path("userId") userId: String
    ): List<UserRepo>
}