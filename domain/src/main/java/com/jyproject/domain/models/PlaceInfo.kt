package com.jyproject.domain.models

data class PlaceInfo(
    val livePeopleInfo: String?,
    val livePeopleInfoMsg: String?,
    val ageRateList: List<Float?>?,
    val predictionList: List<PredictionInfo?>?
)

data class PredictionInfo(
    val time: String?,
    val predictionInfo: String?
)