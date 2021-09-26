package com.fernandomantoan.chucknorrisapp.data.remote

import com.fernandomantoan.chucknorrisapp.entity.ChuckNorrisResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisService {
    @GET("jokes/categories")
    suspend fun getCategories(): List<String>

    @GET("jokes/search")
    suspend fun getFactsFromTextSearch(@Query("query") textSearch: String): ChuckNorrisResponse
}