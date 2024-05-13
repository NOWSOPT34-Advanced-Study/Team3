package com.sopt.now.feature.auth

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
        initAutoLoginStateObserve()
        initBtnClickListener()
        initSignUpStateObserve()
    }

    private fun initAutoLoginStateObserve() {
        viewModel.autoLoginState.flowWithLifecycle(lifecycle).onEach { isAutoLogin ->
            when (isAutoLogin) {
                true -> {
                    // TODO 홈 프레그먼트로 이동
                }

                false -> return@onEach
            }
        }.launchIn(lifecycleScope)
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
            // TODO 회원가입 프레그먼트로 이동
        }
    }

    private fun initSignUpStateObserve() {
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast(getString(R.string.login_completed, getString(R.string.login)))
                    viewModel.saveCheckLoginSharedPreference(true)
                    // TODO 홈 프레그먼트로 이동
                }

                is UiState.Failure -> snackBar(binding.root, state.errorMessage)
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }
}
