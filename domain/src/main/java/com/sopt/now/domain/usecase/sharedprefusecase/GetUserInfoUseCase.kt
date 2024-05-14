package com.sopt.now.domain.usecase.sharedprefusecase

import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(): UserEntity {
        return userInfoRepository.getUserInfo()
    }
}
