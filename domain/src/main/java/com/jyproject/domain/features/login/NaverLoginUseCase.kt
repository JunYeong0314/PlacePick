package com.jyproject.domain.features.login

import javax.inject.Inject

class NaverLoginUseCase @Inject constructor(
    private val naverLoginRepository: NaverLoginRepository
){
    operator fun invoke(context: Any, updateSocialToken: (String?) -> Unit) {
        naverLoginRepository.startNaverLogin(context = context) { updateSocialToken(it) }
    }

}