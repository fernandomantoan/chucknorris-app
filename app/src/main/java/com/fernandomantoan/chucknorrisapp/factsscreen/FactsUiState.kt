package com.fernandomantoan.chucknorrisapp.factsscreen

import com.fernandomantoan.chucknorrisapp.entity.Fact

data class FactsUiState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val facts: List<Fact> = listOf()
) {
    sealed class Error {
        object NetworkError: Error()
        object BackendError: Error()
    }
}