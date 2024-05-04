package com.jyproject.domain.features.login.usecase

import com.jyproject.domain.features.login.repository.NaverLoginRepository
import javax.inject.Inject

class NaverLoginUseCase @Inject constructor(
    private val naverLoginRepository: NaverLoginRepository
){
    operator fun invoke(
        context: Any,
        updateSocialToken: (String?) -> Unit,
        userNum: (String?) -> Unit
    ) {
        naverLoginRepository.startNaverLogin(context, updateSocialToken, userNum)
    }

}