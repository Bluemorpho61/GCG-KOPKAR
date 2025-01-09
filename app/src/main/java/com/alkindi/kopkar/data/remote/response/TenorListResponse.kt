package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class TenorListResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<TenorListItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TenorListItem(

	@field:SerializedName("tenor")
	val tenor: Float? = null,

	@field:SerializedName("provisi")
	val provisi: Any? = null,

	@field:SerializedName("jasa")
	val jasa: Any? = null,

	@field:SerializedName("id_tenor")
	val idTenor: String? = null,

	@field:SerializedName("pjm_code")
	val pjmCode: String? = null,

	@field:SerializedName("asuransi")
	val asuransi: Any? = null,

	@field:SerializedName("batang")
	val batang: Any? = null
)
