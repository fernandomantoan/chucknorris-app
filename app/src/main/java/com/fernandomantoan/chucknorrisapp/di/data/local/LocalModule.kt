package com.fernandomantoan.chucknorrisapp.di.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.fernandomantoan.chucknorrisapp.data.local.AppDatabase
import com.fernandomantoan.chucknorrisapp.data.local.dao.CategoryDao
import com.fernandomantoan.chucknorrisapp.data.local.dao.RecentSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("chucknorris", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideRecentSearchDao(appDatabase: AppDatabase): RecentSearchDao {
        return appDatabase.recentSearchDao()
    }
}