package com.fernandomantoan.chucknorrisapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey var id: Long?,
    var name: String
)