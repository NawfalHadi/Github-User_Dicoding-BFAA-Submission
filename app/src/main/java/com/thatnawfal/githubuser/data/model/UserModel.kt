package com.thatnawfal.githubuser.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val avatar: Int?,
    val company: String?,
    val follower: Int?,
    val following: Int?,
    val location: String?,
    val name: String?,
    val repository: Int?,
    val username: String?
) : Parcelable