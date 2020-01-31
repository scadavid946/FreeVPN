package com.info.uitc.freeandr.api.model

data class ValidateResponse(
    var error: String,
    var success: String?,
    var token: String?
)