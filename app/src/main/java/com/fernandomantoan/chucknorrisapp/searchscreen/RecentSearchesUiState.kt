package com.fernandomantoan.chucknorrisapp.searchscreen

import com.fernandomantoan.chucknorrisapp.entity.RecentSearch

data class RecentSearchesUiState(
    val isLoading: Boolean = false,
    val recentSearches: List<RecentSearch> = listOf()
)