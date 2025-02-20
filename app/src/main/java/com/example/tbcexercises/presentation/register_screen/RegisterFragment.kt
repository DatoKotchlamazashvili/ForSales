package com.example.tbcexercises.presentation.register_screen


import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.collectLastState
import com.example.tbcexercises.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        observeViewModel()
    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etUserName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etPasswordRepeat.text.toString()

            if (password == repeatPassword) {
                viewModel.signup(name, email, password)
            } else {
                toast(getString(R.string.error_password_mismatch))
            }
        }
    }

    private fun observeViewModel() {

        collectLastState(viewModel.validationEvent) { event ->
            when (event) {
                is RegisterUiEvent.Error -> toast(getString(event.messageResId))
            }
        }


        collectLastState(viewModel.signUpState) { resource ->
            when (resource) {
                is Resource.Loading -> showLoadingScreen(true)
                is Resource.Success -> {
                    showLoadingScreen(false)
                    navigateToLoginScreen()
                }

                is Resource.Error -> {
                    showLoadingScreen(false)
                    toast(resource.message)
                }

                null -> {}
            }
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        val viewVisibility = if (!isLoading) View.VISIBLE else View.GONE
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            etEmail.visibility = viewVisibility
            btnRegister.visibility = viewVisibility
            textInputLayout.visibility = viewVisibility
            textInputLayoutRepeat.visibility = viewVisibility
        }
    }

    private fun navigateToLoginScreen() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val authData = bundleOf("email" to email, "password" to password)
        setFragmentResult("authData", authData)
        findNavController().popBackStack()
    }
}

