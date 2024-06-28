package com.interview.scotiaghsearch.data.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("name")
    val name: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)
