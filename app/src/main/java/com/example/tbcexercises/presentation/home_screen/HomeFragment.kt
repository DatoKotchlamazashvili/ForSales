package com.example.tbcexercises.presentation.home_screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.home_screen.product_list_adapter.ProductHomeAdapter
import com.example.tbcexercises.presentation.home_screen.product_load_adapter.ProductLoadStateAdapter
import com.example.tbcexercises.utils.collectLastState
import com.example.tbcexercises.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    private val productHomeAdapter by lazy {
        ProductHomeAdapter(onClick = {
            onClickProduct(it)
        }, onFavouriteClick = { viewModel.setFavouriteStrategy(it) })
    }

    override fun start() {
        setUpRecycleView()

        collectLastState(viewModel.productFlow) {
            productHomeAdapter.submitData(it)
        }

    }

    override fun listeners() {
        binding.swipeRefresh.setOnRefreshListener {
            productHomeAdapter.refresh()

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

    private fun setUpRecycleView() {
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

    private fun onClickProduct(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }

}