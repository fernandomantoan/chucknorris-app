package com.fernandomantoan.chucknorrisapp.data.repository

import com.fernandomantoan.chucknorrisapp.data.local.dao.CategoryDao
import com.fernandomantoan.chucknorrisapp.data.remote.ChuckNorrisService
import com.fernandomantoan.chucknorrisapp.entity.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val chuckNorrisService: ChuckNorrisService,
    private val categoryDao: CategoryDao
) {
    suspend fun getCategories(): List<Category> {
        var offlineCategories = categoryDao.fetchAll()
        if (offlineCategories.isEmpty()) {
            val onlineCategories = chuckNorrisService.getCategories()
            syncCategories(onlineCategories)
            offlineCategories = categoryDao.fetchAll()
        }

        return getRandomCategories(offlineCategories)
    }

    fun getRandomCategories(categories: List<Category>): List<Category> {
        return categories.asSequence().shuffled().take(NUMBER_OF_CATEGORIES).toList()
    }

    private suspend fun syncCategories(categories: List<String>) {
        for (category in categories) {
            val offlineCategory = Category(null, category)
            categoryDao.insert(offlineCategory)
        }
    }

    companion object {
        const val NUMBER_OF_CATEGORIES = 8
    }
}