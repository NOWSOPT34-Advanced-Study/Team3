package com.sopt.now.datasource

import com.sopt.now.dto.UserDto

interface SharedPreferenceDataSource {
    var checkLogin: Boolean
    fun saveUserInfo(userDto: UserDto?)
    fun getUserInfo(): UserDto

    fun clear()
}
