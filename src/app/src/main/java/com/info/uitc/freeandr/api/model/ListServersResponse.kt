package com.info.uitc.freeandr.api.model

data class ListServersResponse(
    var error: String,
    var servers: List<ServerInfo>?
) {

    data class ServerInfo(
        var id: Int,
        var name: String
    )
}