package com.jyproject.data.response.auth


import com.squareup.moshi.Json

data class RegisterResponse(
    @field:Json(name = "token")
    val token: String?
)