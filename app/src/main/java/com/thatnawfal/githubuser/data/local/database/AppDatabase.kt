package com.thatnawfal.githubuser.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thatnawfal.githubuser.data.local.database.dao.FavoriteDao
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favoriteDao() : FavoriteDao

    companion object {
        private const val DB_NAME = "local_storage.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(ctx: Context): AppDatabase {
            return INSTANCE ?: synchronized(AppDatabase::class.java) {

                val instances = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java, DB_NAME)
                    .build()

//                INSTANCE = instances
                instances
                }
            }
        }
    }