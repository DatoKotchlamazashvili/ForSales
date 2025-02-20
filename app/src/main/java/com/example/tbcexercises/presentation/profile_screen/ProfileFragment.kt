package com.example.tbcexercises.presentation.profile_screen


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.databinding.FragmentProfileBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    override fun start() {
        binding.txtEmail.text = viewModel.user.email
        binding.txtUsername.text = viewModel.user.displayName
    }

    override fun listeners() {

        binding.btnLogout.setOnClickListener {
            viewModel.signOut()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToNavigation())
        }
    }
}