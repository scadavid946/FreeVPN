package com.info.uitc.freeandr.api.model

data class ValidateRequest(
    var command: String,
    var email: String,
    var password: String
)