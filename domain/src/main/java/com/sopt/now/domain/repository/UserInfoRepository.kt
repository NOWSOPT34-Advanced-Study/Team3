package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.UserEntity

interface UserInfoRepository {
    suspend fun saveUserInfo(user: UserEntity)
    fun saveCheckLogin(isChecked: Boolean)
    suspend fun getUserInfo(): UserEntity
    fun getCheckLogin(): Boolean
    suspend fun clear()
}
