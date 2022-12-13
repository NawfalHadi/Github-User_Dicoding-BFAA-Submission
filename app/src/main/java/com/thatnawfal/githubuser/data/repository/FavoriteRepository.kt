package com.thatnawfal.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.thatnawfal.githubuser.data.local.database.AppDatabase
import com.thatnawfal.githubuser.data.local.database.dao.FavoriteDao
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import com.thatnawfal.githubuser.utils.AppExecutors
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(
    private val mFavDao: FavoriteDao,
    private val executorService : AppExecutors
) {
    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavDao.getAllFavorite()

    fun addFavorite(favorite: FavoriteEntity) {
        executorService.diskIO.execute { mFavDao.addFavorite(favorite) }
    }

    fun removeFavorite(favorite: FavoriteEntity){
        executorService.diskIO.execute { mFavDao.removeFavorite(favorite) }
    }

    fun checkFavorite(id: Int) : Boolean{
        return mFavDao.isFavorited(id) == 1
    }

}