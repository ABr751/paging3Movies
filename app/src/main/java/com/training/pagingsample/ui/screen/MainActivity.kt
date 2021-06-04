package com.training.pagingsample.ui.screen

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.training.pagingsample.R
import com.training.pagingsample.ui.adapters.LocalMovieAdapter
import com.training.pagingsample.ui.adapters.MovieLoadStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var localMovieAdapter: LocalMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        localMovieAdapter = LocalMovieAdapter()

        localMovieRV.apply {
            setHasFixedSize(true)
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            adapter = localMovieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { localMovieAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.localMovies.collectLatest {
                localMovieAdapter.submitData(it)
            }
        }

        viewModel.searchTerm.observe(this, Observer {
            fetchResults()
        })

        searchMSV?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return if (!newText.isNullOrEmpty() && newText.length >= 3) {
                    viewModel.triggerSearch = true
                    viewModel.searchTerm.value = newText
                    true
                } else if (!newText.isNullOrEmpty() && newText.length <= 3 && viewModel.triggerSearch) {
                    viewModel.triggerSearch = false
                    fetchResults(true)
                    true
                } else {
                    false
                }
            }
        })

        searchMSV?.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {}

            override fun onSearchViewClosed() {
                viewModel.triggerSearch = false
                fetchResults(true)
            }
        })

        // show the loading state for te first load
        localMovieAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                // Show ProgressBar
                progressBar.visibility = View.VISIBLE
            } else {
                if (localMovieAdapter.itemCount == 0) noMoviesLabel.visibility = View.VISIBLE
                else noMoviesLabel.visibility = View.GONE
                // Hide ProgressBar
                progressBar.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun fetchResults(reset: Boolean = false) {
        lifecycleScope.launch {
            viewModel.fetchLocalMovies(reset).collectLatest {
                localMovieAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_search) {
            searchMSV?.showSearch(true)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            localMovieRV.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 7)
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            localMovieRV.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 3)
            }
        }
    }
}
