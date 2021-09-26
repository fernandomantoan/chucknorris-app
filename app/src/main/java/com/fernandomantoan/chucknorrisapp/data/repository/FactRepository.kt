package com.fernandomantoan.chucknorrisapp.data.repository

import com.fernandomantoan.chucknorrisapp.data.remote.ChuckNorrisService
import com.fernandomantoan.chucknorrisapp.entity.Fact
import javax.inject.Inject

class FactRepository @Inject constructor(
    private val chuckNorrisService: ChuckNorrisService
) {
    suspend fun searchFacts(searchTerm: String): List<Fact> {
        val response = chuckNorrisService.getFactsFromTextSearch(searchTerm)
        return response.result
    }
}