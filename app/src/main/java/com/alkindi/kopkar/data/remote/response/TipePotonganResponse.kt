package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class TipePotonganResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<TipePotonganItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TipePotonganItem(

	@field:SerializedName("pitcode")
	val pitcode: String? = null,

	@field:SerializedName("pjm_code")
	val pjmCode: String? = null,

	@field:SerializedName("id_pay")
	val idPay: String? = null
)
