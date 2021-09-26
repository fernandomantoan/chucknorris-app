package com.fernandomantoan.chucknorrisapp.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandomantoan.chucknorrisapp.data.repository.CategoryRepository
import com.fernandomantoan.chucknorrisapp.data.repository.FactRepository
import com.fernandomantoan.chucknorrisapp.factsscreen.FactsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val factRepository: FactRepository
): ViewModel() {
    private val _categoryStateFlow = MutableStateFlow(CategoriesUiState())
    val categoryStateFlow = _categoryStateFlow.asStateFlow()

    fun loadCategories() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exc -> onCategoryError(exc) }
        _categoryStateFlow.value = CategoriesUiState(isLoading = true)

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val categories = categoryRepository.getCategories()
            _categoryStateFlow.value = CategoriesUiState(isLoading = false, categories = categories)
        }
    }

    private fun onCategoryError(exception: Throwable) {
        if (exception is HttpException) {
            _categoryStateFlow.value = CategoriesUiState(error = CategoriesUiState.Error.BackendError)
        } else {
            _categoryStateFlow.value = CategoriesUiState(error = CategoriesUiState.Error.NetworkError)
        }
    }
}