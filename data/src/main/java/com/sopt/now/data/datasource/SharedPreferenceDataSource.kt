package com.sopt.now.data.datasource

import com.sopt.now.data.dto.UserDto

interface SharedPreferenceDataSource {
    var checkLogin: Boolean
    suspend fun saveUserInfo(userDto: UserDto?)
    suspend fun getUserInfo(): UserDto
    suspend fun clear()
}
