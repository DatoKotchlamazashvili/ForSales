package com.example.tbcexercises.presentation.home_screen

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.home_screen.product_list_adapter.ProductHomeAdapter
import com.example.tbcexercises.utils.collectLastState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val productHomeAdapter by lazy {
        ProductHomeAdapter()
    }

    override fun start() {

        binding.rvProducts.adapter = productHomeAdapter

        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)

        collectLastState(viewModel.usersFlow) {
            productHomeAdapter.submitData(it)

            it.map { data ->
                Log.d("products", data.toString())
            }
        }


        super.start()
    }
}