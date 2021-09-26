package com.fernandomantoan.chucknorrisapp.searchscreen

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fernandomantoan.chucknorrisapp.R
import com.fernandomantoan.chucknorrisapp.databinding.ActivitySearchBinding
import com.fernandomantoan.chucknorrisapp.entity.Category
import com.fernandomantoan.chucknorrisapp.factsscreen.FactsActivity
import com.fernandomantoan.chucknorrisapp.factsscreen.FactsUiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchActivity: AppCompatActivity(), TextView.OnEditorActionListener,
    CategoryAdapter.CategoryClickListener {
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var viewBinding: ActivitySearchBinding
    private lateinit var categoriesAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupRecyclerViews()

        viewBinding.searchEditText.setOnEditorActionListener(this)

        lifecycleScope.launchWhenStarted {
            searchViewModel.categoryStateFlow.collect { setCategoriesUiState(it) }
        }

        searchViewModel.loadCategories()
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            doSearch()
        }
        return true
    }

    private fun setupRecyclerViews() {
        // Categories
        categoriesAdapter = CategoryAdapter(this)
        viewBinding.suggestedRecyclerView.layoutManager = GridLayoutManager(this, 4)
        viewBinding.suggestedRecyclerView.setHasFixedSize(true)
        viewBinding.suggestedRecyclerView.adapter = categoriesAdapter
        // Recent Searches
    }

    private fun setCategoriesUiState(uiState: CategoriesUiState) {
        if (uiState.isLoading) {
            showLoadingCategories()
        } else {
            when(uiState.error) {
                CategoriesUiState.Error.NetworkError -> showCategoriesError(R.string.network_error)
                CategoriesUiState.Error.BackendError -> showCategoriesError(R.string.backend_error)
                else -> showCategories(uiState.categories)
            }
        }
    }

    private fun showLoadingCategories() {

    }

    private fun hideLoadingCategories() {

    }

    private fun showCategories(categories: List<Category>) {
        hideLoadingCategories()
        categoriesAdapter.setCategories(categories)
    }

    private fun doSearch() {
        viewBinding.searchLayout.error = null
        viewBinding.searchLayout.isErrorEnabled = false
        if (viewBinding.searchEditText.text != null && viewBinding.searchEditText.text!!.isNotEmpty()) {
            goToSearch(viewBinding.searchEditText.text.toString())
        } else {
            viewBinding.searchLayout.error = getString(R.string.search_term_required)
            viewBinding.searchLayout.isErrorEnabled = true
        }
    }

    private fun goToSearch(term: String) {
        val intent = Intent().also {
            it.putExtra(FactsActivity.SEARCH_TERM, term)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun categoryClicked(category: Category) {
        goToSearch(category.name)
    }

    private fun showCategoriesError(messageResource: Int) {
        hideLoadingCategories()
        Snackbar.make(viewBinding.root, getString(messageResource), Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                searchViewModel.loadCategories()
            }
            .show()
    }
}