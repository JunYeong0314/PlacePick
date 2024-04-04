package com.jyproject.domain.features.login

import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val kakaoLoginRepository: KakaoLoginRepository
) {
    operator fun invoke(updateSocialToken: (String?) -> Unit){
        kakaoLoginRepository.startKakaoLogin { updateSocialToken(it) }
    }
}