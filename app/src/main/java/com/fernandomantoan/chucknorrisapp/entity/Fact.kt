package com.fernandomantoan.chucknorrisapp.entity

data class Fact(
    val icon_url: String,
    val id: String,
    val url: String,
    val value: String,
    val categories: List<String>
)
