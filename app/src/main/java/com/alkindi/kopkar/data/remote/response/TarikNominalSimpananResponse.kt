package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class TarikNominalSimpananResponse(

	@field:SerializedName("docnum")
	val docnum: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("details")
	val details: String? =null
)
