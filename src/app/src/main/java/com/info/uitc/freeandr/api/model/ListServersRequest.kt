package com.info.uitc.freeandr.api.model

data class ListServersRequest(
    var command: String,
    var email: String,
    var token: String
)