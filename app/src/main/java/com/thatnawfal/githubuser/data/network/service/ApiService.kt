package com.thatnawfal.githubuser.data.network.service

import com.thatnawfal.githubuser.data.model.response.DetailUsersModel
import com.thatnawfal.githubuser.data.model.response.SearchResponse
import com.thatnawfal.githubuser.data.model.response.UsersModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/users?")
    @Headers("Authorization: token $token")
    fun getUsers(
        @Query("per_page") per_page: Int,
    ): Call<List<UsersModel>>

    @GET("/search/users?")
    @Headers("Authorization: token $token")
    fun searchUsers(
        @Query("q") q: String,
        @Query("per_page") per_page: Int,
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $token")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUsersModel>

    @GET("users/{username}/{follow}?")
    @Headers("Authorization: token $token")
    fun getFollowsList(
        @Path("username") username: String,
        @Path("follow") follow: String,
        @Query("per_page") per_page: Int,
    ): Call<List<UsersModel>>

    companion object {
        const val token = "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"
    }
}