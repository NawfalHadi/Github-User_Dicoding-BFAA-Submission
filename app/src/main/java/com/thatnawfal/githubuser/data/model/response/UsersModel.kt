package com.thatnawfal.githubuser.data.model.response


import com.google.gson.annotations.SerializedName

data class UsersModel(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?
)