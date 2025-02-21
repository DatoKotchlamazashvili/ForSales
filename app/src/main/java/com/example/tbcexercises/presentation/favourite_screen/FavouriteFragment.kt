package com.example.tbcexercises.presentation.favourite_screen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentFavouriteBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.favourite_screen.favorute_product_adapter.FavouriteProductAdapter
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.collectLastState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteFragment :
    BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate) {
    private val viewModel: FavouriteViewModel by viewModels()

    private val favouriteProductAdapter by lazy {
        FavouriteProductAdapter()
    }

    override fun start() {

        binding.rvProducts.apply {
            adapter = favouriteProductAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        collectLastState(viewModel.favouriteProducts) { state ->
            when (state) {
                is Resource.Error -> {
                    showLoadingScreen(false)
                }

                Resource.Loading -> {
                    showLoadingScreen(true)

                }

                is Resource.Success -> {
                    showLoadingScreen(false)
                    favouriteProductAdapter.submitList(state.data.toList())

                }

                null -> {

                }
            }

        }
    }

    private fun showLoadingScreen(isVisible: Boolean) {
        binding.apply {
            rvProducts.isVisible = !isVisible
            progressBar.isVisible = isVisible
            txtError.isVisible = !isVisible
        }

    }
}