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

	@field:SerializedName("sskr")
	val sskr: Any? = null,

	@field:SerializedName("iup")
	val iup: Any? = null,

	@field:SerializedName("mbrid")
	val mbrid: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("iuw")
	val iuw: Any? = null,

	@field:SerializedName("ply")
	val ply: Any? = null
)
