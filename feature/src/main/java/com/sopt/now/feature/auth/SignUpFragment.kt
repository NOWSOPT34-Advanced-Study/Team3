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
import com.sopt.now.feature.databinding.FragmentSignUpBinding
import com.sopt.now.feature.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpFragment : BindingFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun initView() {
        initSignUpBtnClickListener()
        initSignUpStateObserve()
    }

    private fun initSignUpBtnClickListener() {
        binding.btnSignUp.setOnClickListener {
            viewModel.setUser(getUserInputData())
        }
    }

    private fun getUserInputData(): User {
        return with(binding) {
            User(
                id = etSignUpId.text.toString(),
                password = etSignUpPwd.text.toString(),
                nickName = etSignUpNickname.text.toString(),
                mbti = etSignUpMbti.text.toString()
            )
        }
    }

    private fun initSignUpStateObserve() {
        viewModel.signUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast(getString(R.string.login_completed, getString(R.string.sign_up)))
                    viewModel.saveUserInfoSharedPreference(state.data.toUserEntity())
                    findNavController().navigate(R.id.fragment_login)
                }

                is UiState.Failure -> {
                    snackBar(binding.root, state.errorMessage)
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

}
