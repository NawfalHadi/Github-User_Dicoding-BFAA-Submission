package com.thatnawfal.githubuser.data.network.service

import com.thatnawfal.githubuser.data.model.response.UsersModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/users?")
    fun getUsers(
        @Query("per_page") per_page : Int
    ): Call<List<UsersModel>>
}