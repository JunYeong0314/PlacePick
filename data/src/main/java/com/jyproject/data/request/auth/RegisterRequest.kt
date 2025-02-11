package com.jyproject.data.request.auth

import com.squareup.moshi.Json

data class RegisterRequest(
    @field:Json(name = "userNum")
    val userNum: String?,
    @field:Json(name = "nick")
    val nick: String?
)
