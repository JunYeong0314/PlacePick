package com.jyproject.data.response.auth


import com.squareup.moshi.Json

data class CheckResponse(
    @Json(name = "response")
    val response: Response?
)

data class Response(
    @Json(name = "exists")
    val exists: Boolean?,
    @Json(name = "token")
    val token: String?
)