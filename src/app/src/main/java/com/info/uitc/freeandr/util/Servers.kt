package com.info.uitc.freeandr.util

import com.info.uitc.freeandr.model.Server

class Servers {
    companion object {
        fun getAllServers() : List<Server> {
            return mutableListOf(
                Server("United State", "us", "10.10.1.1"),
                Server("Canada", "ca", "20.20.2.2")
            )
        }
    }
}