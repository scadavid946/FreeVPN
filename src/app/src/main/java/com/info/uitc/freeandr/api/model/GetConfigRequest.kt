package com.info.uitc.freeandr.api.model

data class GetConfigRequest(
    var command: String,
    var email: String,
    var token: String
)