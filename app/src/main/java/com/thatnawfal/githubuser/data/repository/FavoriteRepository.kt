package com.thatnawfal.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.thatnawfal.githubuser.data.local.database.AppDatabase
import com.thatnawfal.githubuser.data.local.database.dao.FavoriteDao
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = AppDatabase.getInstance(application)
        mFavDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavDao.getAllFavorite()

    fun addFavorite(favorite: FavoriteEntity) {
        executorService.execute { mFavDao.addFavorite(favorite) }
    }

    fun removeFavorite(favorite: FavoriteEntity){
        executorService.execute { mFavDao.removeFavorite(favorite) }
    }

    fun checkFavorite(id: Int) : Boolean{
        return mFavDao.isFavorited(id) == 1
    }

}