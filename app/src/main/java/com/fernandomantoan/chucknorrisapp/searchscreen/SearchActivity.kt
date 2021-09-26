package com.fernandomantoan.chucknorrisapp.searchscreen

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fernandomantoan.chucknorrisapp.R
import com.fernandomantoan.chucknorrisapp.databinding.ActivitySearchBinding
import com.fernandomantoan.chucknorrisapp.factsscreen.FactsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: AppCompatActivity(), TextView.OnEditorActionListener {
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var viewBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.searchEditText.setOnEditorActionListener(this)
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            doSearch()
        }
        return true
    }

    private fun doSearch() {
        viewBinding.searchLayout.error = null
        viewBinding.searchLayout.isErrorEnabled = false
        if (viewBinding.searchEditText.text != null && viewBinding.searchEditText.text!!.isNotEmpty()) {
            val intent = Intent().also {
                it.putExtra(FactsActivity.SEARCH_TERM, viewBinding.searchEditText.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        } else {
            viewBinding.searchLayout.error = getString(R.string.search_term_required)
            viewBinding.searchLayout.isErrorEnabled = true
        }
    }
}