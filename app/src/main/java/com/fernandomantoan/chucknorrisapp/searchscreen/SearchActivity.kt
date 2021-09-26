package com.fernandomantoan.chucknorrisapp.searchscreen

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandomantoan.chucknorrisapp.R
import com.fernandomantoan.chucknorrisapp.databinding.ActivitySearchBinding
import com.fernandomantoan.chucknorrisapp.entity.Category
import com.fernandomantoan.chucknorrisapp.entity.RecentSearch
import com.fernandomantoan.chucknorrisapp.factsscreen.FactsActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchActivity: AppCompatActivity(), TextView.OnEditorActionListener,
    CategoryAdapter.CategoryClickListener, RecentSearchAdapter.RecentSearchClickListener {
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var viewBinding: ActivitySearchBinding
    private lateinit var categoriesAdapter: CategoryAdapter
    private lateinit var recentSearchesAdapter: RecentSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupRecyclerViews()

        viewBinding.searchEditText.setOnEditorActionListener(this)

        lifecycleScope.launchWhenStarted {
            searchViewModel.categoryStateFlow.collect { setCategoriesUiState(it) }
        }
        lifecycleScope.launchWhenStarted {
            searchViewModel.recentSearchStateFlow.collect { setRecentSearchesUiState(it) }
        }

        searchViewModel.loadCategories()
        searchViewModel.loadRecentSearches()
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
        recentSearchesAdapter = RecentSearchAdapter(this)
        viewBinding.pastSearchesRecyclerView.layoutManager = LinearLayoutManager(this)
        viewBinding.pastSearchesRecyclerView.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))
        viewBinding.pastSearchesRecyclerView.adapter = recentSearchesAdapter
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

    private fun setRecentSearchesUiState(uiState: RecentSearchesUiState) {
        showRecentSearches(uiState.recentSearches)
    }

    private fun showLoadingCategories() {
        viewBinding.pastSearchesRecyclerView.visibility = View.GONE
        viewBinding.suggestedRecyclerView.visibility = View.GONE
        viewBinding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoadingCategories() {
        viewBinding.pastSearchesRecyclerView.visibility = View.VISIBLE
        viewBinding.suggestedRecyclerView.visibility = View.VISIBLE
        viewBinding.loadingIndicator.visibility = View.GONE
    }

    private fun showCategories(categories: List<Category>) {
        hideLoadingCategories()
        categoriesAdapter.setCategories(categories)
    }

    private fun showRecentSearches(recentSearches: List<RecentSearch>) {
        recentSearchesAdapter.setRecentSearches(recentSearches)
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

    private fun showCategoriesError(messageResource: Int) {
        hideLoadingCategories()
        Snackbar.make(viewBinding.root, getString(messageResource), Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                searchViewModel.loadCategories()
            }
            .show()
    }

    override fun categoryClicked(category: Category) {
        goToSearch(category.name)
    }

    override fun recentSearchClicked(recentSearch: RecentSearch) {
        goToSearch(recentSearch.name)
    }
}