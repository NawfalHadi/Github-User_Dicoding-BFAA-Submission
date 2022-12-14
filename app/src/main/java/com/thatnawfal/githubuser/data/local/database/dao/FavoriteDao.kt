package com.thatnawfal.githubuser.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert
    fun addFavorite(favorite: FavoriteEntity)

    @Delete
    fun removeFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favoriteentity ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favoriteentity ORDER BY id ASC LIMIT 6")
    fun getSomeFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT COUNT() FROM favoriteentity WHERE id = :id")
    fun isFavorited(id: Int): Int
}