package com.fernandomantoan.chucknorrisapp.data.repository

import com.fernandomantoan.chucknorrisapp.data.local.dao.RecentSearchDao
import com.fernandomantoan.chucknorrisapp.data.remote.ChuckNorrisService
import com.fernandomantoan.chucknorrisapp.entity.Fact
import com.fernandomantoan.chucknorrisapp.entity.RecentSearch
import java.util.*
import javax.inject.Inject

class FactRepository @Inject constructor(
    private val chuckNorrisService: ChuckNorrisService,
    private val recentSearchDao: RecentSearchDao
) {
    suspend fun searchFacts(searchTerm: String): List<Fact> {
        persistRecentSearch(searchTerm)
        val response = chuckNorrisService.getFactsFromTextSearch(searchTerm)
        return response.result
    }

    suspend fun fetchRecentSearches(): List<RecentSearch> {
        return recentSearchDao.fetchRecentSearches()
    }

    private suspend fun persistRecentSearch(searchTerm: String) {
        val existingSearch = recentSearchDao.findRecentSearchByName(searchTerm)
        if (existingSearch == null) {
            insertNewRecentSearch(searchTerm)
        } else {
            updateExistingSearch(existingSearch)
        }
    }

    private suspend fun insertNewRecentSearch(searchTerm: String) {
        val recentSearch = RecentSearch(null, searchTerm, Date().time.toInt())
        recentSearchDao.insertRecentSearch(recentSearch)
    }

    private suspend fun updateExistingSearch(recentSearch: RecentSearch) {
        recentSearch.date = Date().time.toInt()
        recentSearchDao.updateRecentSearch(recentSearch)
    }
}