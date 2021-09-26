package com.fernandomantoan.chucknorrisapp.factsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandomantoan.chucknorrisapp.data.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FactsViewModel @Inject constructor(
    private val factRepository: FactRepository
): ViewModel() {
    private val _factStateFlow = MutableStateFlow(FactsUiState())
    val factStateFlow = _factStateFlow.asStateFlow()

    fun searchFacts(searchTerm: String) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exc -> onError(exc) }

        _factStateFlow.value = FactsUiState(isLoading = true)

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val facts = factRepository.searchFacts(searchTerm)
            _factStateFlow.value = FactsUiState(isLoading = false, facts = facts)
        }
    }

    private fun onError(exception: Throwable) {
        Timber.e(exception, "An error has occurred!")
        if (exception is HttpException) {
            _factStateFlow.value = FactsUiState(error = FactsUiState.Error.BackendError)
        } else {
            _factStateFlow.value = FactsUiState(error = FactsUiState.Error.NetworkError)
        }
    }
}