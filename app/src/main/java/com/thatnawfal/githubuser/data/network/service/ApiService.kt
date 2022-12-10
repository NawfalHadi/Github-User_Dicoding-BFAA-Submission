package com.thatnawfal.githubuser.data.network.service

import com.thatnawfal.githubuser.data.model.response.DetailUsersModel
import com.thatnawfal.githubuser.data.model.response.SearchResponse
import com.thatnawfal.githubuser.data.model.response.UsersModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    companion object {
        const val tokens = "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"
    }

    @GET("/users?")
    fun getUsers(
        @Query("per_page") per_page: Int,
        @Header("Authorization") token: String = tokens
    ): Call<List<UsersModel>>

    @GET("/search/users?")
    fun searchUsers(
        @Query("q") q: String,
        @Query("per_page") per_page: Int,
        @Header("Authorization") token: String = tokens
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization") token: String = tokens
    ): Call<DetailUsersModel>

    @GET("users/{username}/{follow}?")
    fun getFollowsList(
        @Path("username") username: String,
        @Path("follow") follow: String,
        @Query("per_page") per_page: Int,
        @Header("Authorization") token: String = tokens
    ): Call<List<UsersModel>>
}