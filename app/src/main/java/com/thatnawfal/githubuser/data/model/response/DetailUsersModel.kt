package com.thatnawfal.githubuser.data.model.response


import com.google.gson.annotations.SerializedName

data class DetailUsersModel(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("followers_url")
    val followersUrl: String?,
    @SerializedName("following")
    val following: Int?,
    @SerializedName("following_url")
    val followingUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("public_repos")
    val publicRepos: Int?,
    @SerializedName("url")
    val url: String?
)