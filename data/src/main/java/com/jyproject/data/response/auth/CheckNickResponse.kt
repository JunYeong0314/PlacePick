package com.jyproject.data.response.auth


import com.squareup.moshi.Json

data class CheckNickResponse(
    @Json(name = "message")
    val message: String?
)