package com.kotlin.feature.auth

import android.app.Activity.RESULT_OK
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kotlin.feature.R
import com.kotlin.feature.databinding.ActivitySignUpBinding
import com.kotlin.feature.model.User
import com.kotlin.feature.util.KeyStorage
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.core.view.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
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
                    navigateToLoginActivity(state.data)
                }

                is UiState.Failure -> {
                    snackBar(binding.root, state.errorMessage)
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToLoginActivity(userInputData: User) {
        Intent().apply {
            putExtra(KeyStorage.USER_INPUT, userInputData)
        }.also {
            setResult(RESULT_OK, it)
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}
