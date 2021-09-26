package com.fernandomantoan.chucknorrisapp.entity

data class ChuckNorrisResponse(
    val total: Int,
    val result: List<Fact>
)