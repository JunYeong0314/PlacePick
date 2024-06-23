package com.jyproject.presentation.mappers

import com.jyproject.presentation.R
import javax.inject.Inject

class UiMapper @Inject constructor() {
    fun mapperLivePeopleColor(liveState: String): Int {
        return when(liveState){
            "여유" -> R.color.state_relax
            "보통" -> R.color.state_normal
            "약간 붐빔" -> R.color.state_light_hard
            "붐빔" -> R.color.state_hard
            else -> R.color.light_gray_hard1
        }
    }
}