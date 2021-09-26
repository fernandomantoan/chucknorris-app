package com.fernandomantoan.chucknorrisapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches")
data class RecentSearch(
    @PrimaryKey var id: Long?,
    var name: String,
    var date: Int
)