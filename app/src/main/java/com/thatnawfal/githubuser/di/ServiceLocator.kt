package com.thatnawfal.githubuser.di

import android.content.Context
import com.thatnawfal.githubuser.data.local.database.AppDatabase
import com.thatnawfal.githubuser.data.network.service.ApiClient
import com.thatnawfal.githubuser.data.network.service.ApiService
import com.thatnawfal.githubuser.data.repository.FavoriteRepository
import com.thatnawfal.githubuser.utils.AppExecutors

object ServiceLocator {

    private fun provideAppDatabase(ctx: Context): AppDatabase {
        return AppDatabase.getInstance(ctx)
    }

    private fun provideApiService(): ApiService {
        return ApiClient.instances()
    }

    fun provideFavoriteRepository(ctx: Context): FavoriteRepository {
        return FavoriteRepository(
            provideAppDatabase(ctx).favoriteDao(),
            AppExecutors(),
        )
    }

}