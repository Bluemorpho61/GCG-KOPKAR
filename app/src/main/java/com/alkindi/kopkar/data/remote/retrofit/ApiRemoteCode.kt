package com.alkindi.kopkar.data.remote.retrofit

object ApiRemoteCode {
    const val GET_USER_PERSONAL_DATA =
        "gS%2BZtyMBHTdgEoheRgK6hoGn9gB9jdSeepx4X6/t2uDtvQTu57s32w%3D%3D"
    const val GET_USER_PROFILE_INFO = "KvRnqbr%2BktvsOVY89qJvEdkJAcJmFdyI7GQ98Ln6DH4%3D"

    fun apiUrlArranger(
        baseUrl: String,
        apiDevCode: String,
        apiWorkspaceCode: String,
        apiRemoteCode: String,
        vars: String?
    ): String {
        return if (vars.isNullOrEmpty()) {
            "${baseUrl}txn?fnc=runLib;opic=${apiDevCode};csn=${apiWorkspaceCode};rc=${apiRemoteCode}"
        } else {
            "${baseUrl}txn?fnc=runLib;opic=${apiDevCode};csn=${apiWorkspaceCode};rc=${apiRemoteCode};vars=${vars}"
        }
    }
}

