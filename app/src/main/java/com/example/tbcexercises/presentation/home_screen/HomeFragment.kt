package com.example.tbcexercises.presentation.home_screen

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.home_screen.category_list_adapter.CategoryListAdapter
import com.example.tbcexercises.presentation.home_screen.product_list_adapter.ProductHomeAdapter
import com.example.tbcexercises.presentation.home_screen.product_load_adapter.ProductLoadStateAdapter
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    private val productHomeAdapter by lazy {
        ProductHomeAdapter(onClick = {
            onClickProduct(it)
        }, onFavouriteClick = { viewModel.setFavouriteStrategy(it) },
            onCartClick = { viewModel.insertCartProduct(it) })
    }

    private val categoryAdapter by lazy {
        CategoryListAdapter(onClick = {
            viewModel.updateCategory(it)
        })
    }

    override fun start() {
        listeners()
        setUpProductsRecycleView()
        setupSearchView()
        setUpCategoryRecycleView()

        collectLastState(viewModel.productFlow) {
            productHomeAdapter.submitData(it)
        }

        collectLastState(viewModel.uiState) { state ->
            updateCategoryUI(state)
        }

        binding.txtError.text = getString(R.string.you_don_t_have_internet_connection)
    }

    private fun updateCategoryUI(state: HomeScreenUiState) {
        val isOnline = state.isOnline ?: false
        binding.apply {
            Log.d("homestate", state.toString())
            categoryProgressBar.isVisible = state.isLoading && isOnline

            txtError.isVisible =
                if (state.isOnline == null) false else !isOnline && productHomeAdapter.itemCount == 0
            imgNoInternetConnection.isVisible =
                if (state.isOnline == null) false else !isOnline && productHomeAdapter.itemCount == 0

            searchView.isVisible = isOnline

            rvCategories.isVisible = isOnline
        }
        state.error?.let {
            toast(message = getString(it))
        }
        if (isOnline && state.categories.isEmpty()) {
            viewModel.getCategories()
        }
        if (!state.isLoading && state.error == null) {
            categoryAdapter.submitList(state.categories)
        }

    }

    private fun listeners() {
        binding.swipeRefresh.setOnRefreshListener {
            productHomeAdapter.refresh()
            viewModel.getCategories()
            binding.swipeRefresh.isRefreshing = false
        }
        productHomeAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.mediator?.refresh ?: loadState.source.refresh

            val isInitialLoading =
                refreshState is LoadState.Loading && productHomeAdapter.itemCount == 0

            binding.apply {
                progressBar.isVisible = isInitialLoading
                rvProducts.isVisible =
                    refreshState is LoadState.NotLoading || productHomeAdapter.itemCount > 0
            }

            handleError(loadState)
        }
    }

    private fun setUpProductsRecycleView() {
        binding.rvProducts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2)

            adapter = productHomeAdapter.withLoadStateFooter(
                footer = ProductLoadStateAdapter(productHomeAdapter::retry)
            )
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            toast(it.error.toString())
        }
    }

    private fun setUpCategoryRecycleView() {
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun onClickProduct(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var searchJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.updateSearchQuery(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        delay(1500)
                        newText?.let { viewModel.updateSearchQuery(it) }
                    }
                }
                return true
            }
        })
    }
}