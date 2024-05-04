package com.jyproject.data.response.auth


import com.squareup.moshi.Json

data class SignUpResponse(
    @field:Json(name = "token")
    val token: String?
)