package com.jyproject.domain.features.login

import javax.inject.Inject

class NaverLoginUseCase @Inject constructor(
    private val naverLoginRepository: NaverLoginRepository
){
    operator fun invoke(updateSocialToken: (String?) -> Unit) {
        naverLoginRepository.startNaverLogin { updateSocialToken(it) }
    }

}