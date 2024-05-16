package com.sopt.now.feature.auth

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.fragment.snackBar
import com.sopt.now.core.util.fragment.toast
import com.sopt.now.core.view.UiState
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val viewModel by viewModels<LoginViewModel>()

    override fun initView() {
        initBtnClickListener()
        initSignUpStateObserve()
    }

    private fun initBtnClickListener() {
        initLoginBtnClickListener()
        initSignUpBtnClickListener()
    }

    private fun initLoginBtnClickListener() = with(binding) {
        btnLogin.setOnClickListener {
            viewModel.setLogin(
                id = etLoginId.text.toString(),
                pwd = etLoginPwd.text.toString()
            )
        }
    }

    private fun initSignUpBtnClickListener() {
        binding.tvLoginSignUp.setOnClickListener {
            findNavController().navigate(R.id.fragment_sign_up)
        }
    }

    private fun initSignUpStateObserve() {
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast(getString(R.string.login_completed, getString(R.string.login)))
                    viewModel.saveCheckLoginSharedPreference(true)
                    findNavController().navigate(R.id.fragment_home)
                }

                is UiState.Failure -> snackBar(binding.root, state.errorMessage)
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }
}
