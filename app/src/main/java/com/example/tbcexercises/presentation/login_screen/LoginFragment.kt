package com.example.tbcexercises.presentation.login_screen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        observeUiState()
        registerListeners()
        listeners()
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val rememberMe = binding.cbRememberMe.isChecked

            viewModel.login(email, password, rememberMe)
        }

        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeUiState() {
        collectLastState(viewModel.uiState) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: LoginScreenUiState) {
        showLoadingScreen(state.isLoading)

        state.error?.let { errorMessage ->
            toast(errorMessage)
        }

        if (!state.isLoading && state.user != null) {
            navigateToHomeScreen()
        }

        if (state.email.isNotEmpty() && binding.etEmail.text.toString() != state.email) {
            binding.etEmail.setText(state.email)
        }

        if (state.password.isNotEmpty() && binding.etPassword.text.toString() != state.password) {
            binding.etPassword.setText(state.password)
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
            etEmail.isVisible = !isLoading
            btnLogin.isVisible = !isLoading
            txtRememberMe.isVisible = !isLoading
            cbRememberMe.isVisible = !isLoading
            textInputLayout.isVisible = !isLoading
            txtRegister.isVisible = !isLoading
        }
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
    }

    private fun registerListeners() {
        parentFragmentManager.setFragmentResultListener("authData", this) { _, bundle ->
            val email = bundle.getString("email") ?: ""
            val password = bundle.getString("password") ?: ""

            viewModel.updateCredentials(email, password)
        }
    }
}