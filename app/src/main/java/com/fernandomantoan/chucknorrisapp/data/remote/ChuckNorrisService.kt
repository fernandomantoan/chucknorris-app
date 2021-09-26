package com.fernandomantoan.chucknorrisapp.data.remote

import com.fernandomantoan.chucknorrisapp.entity.ChuckNorrisResponse
import com.fernandomantoan.chucknorrisapp.entity.Fact
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisService {
    @GET("jokes/categories")
    suspend fun getCategories(): List<String>

    @GET("jokes/random")
    suspend fun getFactsFromCategory(@Query("category") category: String): List<Fact>

    @GET("jokes/search")
    suspend fun getFactsFromTextSearch(@Query("query") textSearch: String): ChuckNorrisResponse
}