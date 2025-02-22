package com.example.tbcexercises.presentation.launcher_screen


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.databinding.FragmentLauncherBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.utils.extension.collectLastState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LauncherFragment : BaseFragment<FragmentLauncherBinding>(FragmentLauncherBinding::inflate) {
    private val viewModel: LaunchViewModel by viewModels()
    override fun start() {
        runBlocking {
            delay(1000L)
            collectLastState(viewModel.rememberMeFlow) { rememberMe ->
                if (rememberMe) {
                    findNavController().navigate(
                        LauncherFragmentDirections.actionLauncherFragmentToHomeFragment()
                    )
                } else {
                    findNavController().navigate(LauncherFragmentDirections.actionLauncherFragmentToNavigation())
                }
            }

        }

    }
}