package com.example.tbcexercises.presentation.search_screen


import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentSearchBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.search_screen.product_adapter.SearchProductAdapter
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

    private val searchAdapter by lazy {
        SearchProductAdapter(onFavouriteClick = { viewModel.setFavourite(it) })
    }


    override fun start() {
        setupRecyclerView()
        observeSearchResults()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        binding.rvSearchProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    private fun observeSearchResults() {


        collectLastState(viewModel.searchResultsFlow) { pagingData ->
            searchAdapter.submitData(pagingData)

        }

        searchAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.mediator?.refresh ?: loadState.source.refresh

            val isInitialLoading =
                refreshState is LoadState.Loading
            binding.apply {
                progressBar.isVisible = isInitialLoading
                rvSearchProducts.isVisible =
                    refreshState is LoadState.NotLoading || searchAdapter.itemCount > 0
            }

            handleError(loadState)
        }

    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var searchJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.updateQuery(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        delay(1000)
                        newText?.let { viewModel.updateQuery(it) }
                    }
                }
                return true
            }
        })
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            toast(it.error.toString())
        }
    }
}