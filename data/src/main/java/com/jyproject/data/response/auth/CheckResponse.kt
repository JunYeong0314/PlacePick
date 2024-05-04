package com.jyproject.data.response.auth


import com.squareup.moshi.Json

data class CheckResponse(
    @field:Json(name = "exists")
    val exists: Boolean?,
    @field:Json(name = "token")
    val token: String?
)