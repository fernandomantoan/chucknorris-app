package com.fernandomantoan.chucknorrisapp.factsscreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandomantoan.chucknorrisapp.R
import com.fernandomantoan.chucknorrisapp.databinding.ActivityFactsBinding
import com.fernandomantoan.chucknorrisapp.entity.Fact
import com.fernandomantoan.chucknorrisapp.searchscreen.SearchActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class FactsActivity: AppCompatActivity(), FactsAdapter.FactsClickListener {
    private val factsViewModel: FactsViewModel by viewModels()
    private lateinit var viewBinding: ActivityFactsBinding
    private lateinit var adapter: FactsAdapter
    private var searchTerm: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFactsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        lifecycleScope.launchWhenStarted {
            factsViewModel.factStateFlow.collect { setUiState(it) }
        }

        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_facts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionSearch) {
            openSearchActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = FactsAdapter(this)
        viewBinding.factsRecyclerView.layoutManager = LinearLayoutManager(this)
        viewBinding.factsRecyclerView.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))
        viewBinding.factsRecyclerView.setHasFixedSize(true)
        viewBinding.factsRecyclerView.adapter = adapter
    }

    private fun openSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent, SEARCH_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("On Activity Result...")
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("On Activity Result...")
        if (requestCode == SEARCH_ACTIVITY && resultCode == RESULT_OK) {
            this.searchTerm = data?.getStringExtra(SEARCH_TERM)
            Timber.d("Searching for %s...", searchTerm)
            searchTerm?.let {
                factsViewModel.searchFacts(it)
            }
        }
    }

    private fun setUiState(uiState: FactsUiState) {
        if (uiState.isLoading) {
            showLoading()
        } else {
            when(uiState.error) {
                FactsUiState.Error.NetworkError -> showError(R.string.network_error)
                FactsUiState.Error.BackendError -> showError(R.string.backend_error)
                else -> showFacts(uiState.facts)
            }
        }
    }

    private fun showLoading() {
        viewBinding.factsRecyclerView.visibility = View.GONE
        viewBinding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        viewBinding.factsRecyclerView.visibility = View.VISIBLE
        viewBinding.loadingIndicator.visibility = View.GONE
    }

    private fun showError(messageResource: Int) {
        hideLoading()
        Snackbar.make(viewBinding.root, getString(messageResource), Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                searchTerm?.let {
                    factsViewModel.searchFacts(it)
                }
            }
            .show()
    }

    private fun showFacts(facts: List<Fact>) {
        hideLoading()
        adapter.setFacts(facts)
    }

    override fun factClicked(fact: Fact) {
        Timber.d("Fact clicked %s", fact.url)
    }

    companion object {
        const val SEARCH_ACTIVITY = 1234567
        const val SEARCH_TERM = "search_term"
    }
}