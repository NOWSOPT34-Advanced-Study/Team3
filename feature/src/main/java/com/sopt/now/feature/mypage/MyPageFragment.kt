package com.sopt.now.feature.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.fragment.toast
import com.sopt.now.core.util.intent.navigateTo
import com.sopt.now.feature.R
import com.sopt.now.feature.auth.LoginActivity
import com.sopt.now.feature.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()
    override fun initView() {
        lifecycleScope.launch {
            initBtnClickListener()
            initUpdateUserDataUI()
        }
    }

    private fun initBtnClickListener() {
        initSignOutBtnClickListener()
        initClearInfoBtnClickListener()
    }

    private fun initSignOutBtnClickListener() {
        binding.tvMainSignOut.setOnClickListener {
            viewModel.updateCheckLoginState(false)
            toast(getString(R.string.login_completed, getString(R.string.main_logout_under_bar)))
            navigateTo<LoginActivity>(requireContext())
        }
    }

    private fun initClearInfoBtnClickListener() {
        binding.tvMainClearInfo.setOnClickListener {
            lifecycleScope.launch {
                viewModel.clearSharedPrefUserInfo()
                toast(
                    getString(
                        R.string.login_completed,
                        getString(R.string.main_clear_user_under_bar)
                    )
                )
                navigateTo<LoginActivity>(requireContext())
            }
        }
    }

    private suspend fun initUpdateUserDataUI() = with(binding) {
        viewModel.getSharedPrefUserInfo().apply {
            tvMainIdData.text = id
            tvMainPwdData.text = password
            tvMainNicknameData.text = nickName
            tvMainMbtiData.text = mbti
        }
    }
}
