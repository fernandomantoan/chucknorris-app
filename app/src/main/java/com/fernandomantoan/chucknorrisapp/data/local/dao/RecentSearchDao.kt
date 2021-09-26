package com.fernandomantoan.chucknorrisapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fernandomantoan.chucknorrisapp.entity.RecentSearch

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM recent_searches ORDER BY date DESC")
    suspend fun fetchRecentSearches(): List<RecentSearch>

    @Query("SELECT * FROM recent_searches WHERE name = :name")
    suspend fun findRecentSearchByName(name: String): RecentSearch?

    @Insert
    suspend fun insertRecentSearch(recentSearch: RecentSearch): Long

    @Update
    suspend fun updateRecentSearch(recentSearch: RecentSearch): Int
}