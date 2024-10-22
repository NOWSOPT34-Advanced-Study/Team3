package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.SharedPreferenceDataSource
import com.sopt.now.data.dto.UserDto
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : UserInfoRepository {
    override fun saveUserInfo(user: UserEntity) {
        sharedPreferenceDataSource.saveUserInfo(
            userDto = UserDto(
                id = user.id,
                password = user.password,
                nickName = user.nickName,
                mbti = user.mbti
            )
        )
    }

    override fun saveCheckLogin(isChecked: Boolean) {
        sharedPreferenceDataSource.checkLogin = isChecked
    }

    override fun getUserInfo(): UserEntity {
        return sharedPreferenceDataSource.getUserInfo().toUserEntity()
    }

    override fun getCheckLogin(): Boolean {
        return sharedPreferenceDataSource.checkLogin
    }

    override fun clear() {
        sharedPreferenceDataSource.clear()
    }
}
