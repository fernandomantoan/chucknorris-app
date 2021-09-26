package com.fernandomantoan.chucknorrisapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fernandomantoan.chucknorrisapp.data.local.dao.CategoryDao
import com.fernandomantoan.chucknorrisapp.data.local.dao.RecentSearchDao
import com.fernandomantoan.chucknorrisapp.entity.Category
import com.fernandomantoan.chucknorrisapp.entity.RecentSearch

@Database(entities = [Category::class, RecentSearch::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun recentSearchDao(): RecentSearchDao

    companion object {
        const val DATABASE_NAME = "chucknorris.db"
    }
}