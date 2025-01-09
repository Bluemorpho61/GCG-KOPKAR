package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class NominalSimpananResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("sskq")
	val sskq: Any? = null,

	@field:SerializedName("sskr")
	val sskr: Any? = null,

	@field:SerializedName("sskp")
	val sskp: Any? = null,

	@field:SerializedName("sswj")
	val sswj: Any? = null,

	@field:SerializedName("mbrid")
	val mbrid: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("sspo")
	val sspo: Any? = null,

	@field:SerializedName("company")
	val company: String? = null
)
