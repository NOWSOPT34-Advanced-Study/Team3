package com.sopt.now.data.datasource

import com.sopt.now.data.dto.UserDto

interface SharedPreferenceDataSource {
    var checkLogin: Boolean
    fun saveUserInfo(userDto: UserDto?)
    fun getUserInfo(): UserDto

    fun clear()
}
