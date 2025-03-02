package com.example.tbcexercises.presentation.register_screen


import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.databinding.FragmentRegisterBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        observeUiState()
    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etUserName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etPasswordRepeat.text.toString()
            viewModel.signup(name, email, password,repeatPassword)

        }
    }

    private fun observeUiState() {
        collectLastState(viewModel.uiState) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: RegisterScreenUiState) {
        showLoadingScreen(state.isLoading)

        state.validationError?.let { errorResId ->
            toast(getString(errorResId))
            viewModel.clearValidationError()
        }

        state.error?.let { errorMessage ->
            toast(errorMessage)
        }

        if (!state.isLoading && state.user != null) {
            navigateToLoginScreen()
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
            etEmail.isVisible = !isLoading
            btnRegister.isVisible = !isLoading
            textInputLayout.isVisible = !isLoading
            textInputLayoutRepeat.isVisible = !isLoading
            etUserName.isVisible = !isLoading
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
