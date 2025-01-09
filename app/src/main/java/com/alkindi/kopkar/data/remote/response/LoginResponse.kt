package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("s")
    val s: String? = null,

    @field:SerializedName("srt")
    val srt: String? = null,

    @field:SerializedName("pid")
    val pid: String? = null,

//    In case kalo username/passsword salah
    @field:SerializedName("e")
    val e: String? = null
) {
    fun isSuccess(): Boolean {
        return e == null
    }
}
