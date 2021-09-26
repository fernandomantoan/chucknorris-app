package com.fernandomantoan.chucknorrisapp.searchscreen

import com.fernandomantoan.chucknorrisapp.entity.Category

data class CategoriesUiState(val isLoading: Boolean = false,
                             val error: Error? = null,
                             val categories: List<Category> = listOf()
) {
    sealed class Error {
        object NetworkError: Error()
        object BackendError: Error()
    }
}