package com.jyproject.data.response.place


import com.squareup.moshi.Json

data class PlaceInfoResponse(
    @Json(name = "RESULT")
    val result: PlaceInfoResult?,
    @Json(name = "SeoulRtd.citydata_ppltn")
    val placeInfoData: List<PlaceInfoData?>?
)

data class PlaceInfoResult(
    @Json(name = "RESULT.CODE")
    val resultCode: String?,
    @Json(name = "RESULT.MESSAGE")
    val resultMsg: String?
)

data class PlaceInfoData(
    @Json(name = "AREA_CD")
    val placeCode: String?,
    @Json(name = "AREA_CONGEST_LVL")
    val livePeopleInfo: String?,
    @Json(name = "AREA_CONGEST_MSG")
    val livePeopleInfoMsg: String?,
    @Json(name = "AREA_NM")
    val place: String?,
    @Json(name = "AREA_PPLTN_MAX")
    val maxInfoValue: String?,
    @Json(name = "AREA_PPLTN_MIN")
    val minInfoValue: String?,
    @Json(name = "FCST_PPLTN")
    val predictionObject: List<PredictionInfoData?>?,
    @Json(name = "FCST_YN")
    val isPrediction: String?,
    @Json(name = "FEMALE_PPLTN_RATE")
    val femaleRate: String?,
    @Json(name = "MALE_PPLTN_RATE")
    val maleRate: String?,
    @Json(name = "NON_RESNT_PPLTN_RATE")
    val nonResentPeopleRate: String?,
    @Json(name = "PPLTN_RATE_0")
    val rate0: String?,
    @Json(name = "PPLTN_RATE_10")
    val rate10: String?,
    @Json(name = "PPLTN_RATE_20")
    val rate20: String?,
    @Json(name = "PPLTN_RATE_30")
    val rate30: String?,
    @Json(name = "PPLTN_RATE_40")
    val rate40: String?,
    @Json(name = "PPLTN_RATE_50")
    val rate50: String?,
    @Json(name = "PPLTN_RATE_60")
    val rate60: String?,
    @Json(name = "PPLTN_RATE_70")
    val rate70: String?,
    @Json(name = "PPLTN_TIME")
    val liveUpdateTime: String?,
    @Json(name = "REPLACE_YN")
    val isReplace: String?,
    @Json(name = "RESNT_PPLTN_RATE")
    val resentPeopleRate: String?
)

data class PredictionInfoData(
    @Json(name = "FCST_CONGEST_LVL")
    val predictionPeopleRate: String?,
    @Json(name = "FCST_PPLTN_MAX")
    val predictionMaxRate: String?,
    @Json(name = "FCST_PPLTN_MIN")
    val predictionMinRate: String?,
    @Json(name = "FCST_TIME")
    val predictionTime: String?
)