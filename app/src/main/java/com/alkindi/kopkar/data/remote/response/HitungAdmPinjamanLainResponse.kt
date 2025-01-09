package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class HitungAdmPinjamanLainResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: AdmPinjamanLainItem? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("errors")
    val errors: List<Any?>? = null
)

data class AdmPinjamanLainItem(

    @field:SerializedName("tot_pjm")
    val totPjm: Int? = null,

    @field:SerializedName("provisi")
    val provisi: Int? = null,

    @field:SerializedName("simp_khusus")
    val simpKhusus: Int? = null,

    @field:SerializedName("adm")
    val adm: Int? = null,

    @field:SerializedName("asuransi")
    val asuransi: Any? = null,

    @field:SerializedName("dana_cair")
    val danaCair: Any? = null
)
