package com.thatnawfal.githubuser.data.network.service

import com.thatnawfal.githubuser.BuildConfig
import com.thatnawfal.githubuser.utils.Helper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Helper.api_endpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun instances(): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}