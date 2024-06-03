package com.jyproject.data.mappers

import com.jyproject.data.response.place.PlaceInfoData
import com.jyproject.data.response.search.SearchResponse
import com.jyproject.domain.models.Place
import com.jyproject.domain.models.PlaceInfo
import com.jyproject.domain.models.PredictionInfo
import javax.inject.Inject

class PlaceDataMapper @Inject constructor() {
    fun mapperToPlaces(searchResponse: SearchResponse): List<Place>?{
        return searchResponse.places?.map {
            Place(
                no = it?.no,
                place = it?.place,
                placeArea = it?.placeArea
            )
        }
    }

    fun mapperToPlaceInfo(placeInfoData: PlaceInfoData?): PlaceInfo {
        return PlaceInfo(
            livePeopleInfo = placeInfoData?.livePeopleInfo,
            livePeopleInfoMsg = placeInfoData?.livePeopleInfoMsg,
            ageRateList = mapperToAgeRate(placeInfoData),
            predictionList = mapperToPrediction(placeInfoData)
        )
    }

    private fun mapperToAgeRate(placeInfoData: PlaceInfoData?): List<Float?> {
        return listOf(
            placeInfoData?.rate0?.toFloat(), placeInfoData?.rate10?.toFloat(),
            placeInfoData?.rate20?.toFloat(), placeInfoData?.rate30?.toFloat(),
            placeInfoData?.rate40?.toFloat(), placeInfoData?.rate50?.toFloat(),
            placeInfoData?.rate60?.toFloat()
        )
    }

    private fun mapperToPrediction(placeInfoData: PlaceInfoData?): List<PredictionInfo>? {
        return placeInfoData?.predictionObject?.map {
            PredictionInfo(
                time = it?.predictionTime,
                predictionInfo = it?.predictionPeopleRate
            )
        }
    }
}