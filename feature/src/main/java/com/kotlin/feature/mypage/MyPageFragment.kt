package com.kotlin.feature.mypage

import androidx.fragment.app.viewModels
import com.kotlin.feature.R
import com.kotlin.feature.auth.LoginActivity
import com.kotlin.feature.databinding.FragmentMyPageBinding
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.fragment.toast
import com.sopt.now.core.util.intent.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()
    override fun initView() {
        initBtnClickListener()
        initUpdateUserDataUI()
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

    private fun initUpdateUserDataUI() = with(binding) {
        viewModel.getSharedPrefUserInfo().apply {
            tvMainIdData.text = id
            tvMainPwdData.text = password
            tvMainNicknameData.text = nickName
            tvMainMbtiData.text = mbti
        }
    }
}
