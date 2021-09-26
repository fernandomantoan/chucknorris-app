package com.fernandomantoan.chucknorrisapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fernandomantoan.chucknorrisapp.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    suspend fun fetchAll(): List<Category>

    @Insert
    suspend fun insert(category: Category): Long
}