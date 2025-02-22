package com.example.tbcexercises.presentation.login_screen


import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentLoginBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        observeViewModel()
        registerListeners()
    }

    override fun listeners() {
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

    private fun observeViewModel() {
        collectLastState(viewModel.signInState) { resource ->
            when (resource) {
                is Resource.Loading -> showLoadingScreen(true)
                is Resource.Success -> {
                    showLoadingScreen(false)
                    navigateToHomeScreen()
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
            btnLogin.visibility = viewVisibility
            txtRememberMe.visibility = viewVisibility
            cbRememberMe.visibility = viewVisibility
            textInputLayout.visibility = viewVisibility
            txtRegister.visibility = viewVisibility
        }
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
    }

    private fun registerListeners() {
        parentFragmentManager.setFragmentResultListener("authData", this) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")

            binding.apply {
                etEmail.setText(email)
                etPassword.setText(password)
            }
        }
    }
}
