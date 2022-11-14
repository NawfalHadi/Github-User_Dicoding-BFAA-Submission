package com.thatnawfal.githubuser.data.model.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<UsersModel>?,
    @SerializedName("total_count")
    val totalCount: Int?
)