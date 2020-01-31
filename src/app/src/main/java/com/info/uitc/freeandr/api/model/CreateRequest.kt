package com.info.uitc.freeandr.api.model

data class CreateRequest(
    var command: String,
    var email: String,
    var password: String
)