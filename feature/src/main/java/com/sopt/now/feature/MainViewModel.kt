package com.sopt.now.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.domain.usecase.sharedprefusecase.GetCheckLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCheckLoginUseCase: GetCheckLoginUseCase,
) : ViewModel() {
    private val _autoLoginState = MutableStateFlow<Boolean>(false)
    val autoLoginState: StateFlow<Boolean> get() = _autoLoginState.asStateFlow()

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            _autoLoginState.value = isAutoLogin()
        }
    }

    private fun isAutoLogin(): Boolean = getCheckLoginUseCase.invoke()
}