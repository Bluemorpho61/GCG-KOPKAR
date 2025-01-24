package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class JenisPinjamanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<JenisPinjamanDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class JenisPinjamanDataItem(

	@field:SerializedName("pitcode")
	val pitcode: String? = null,

	@field:SerializedName("last_eff")
	val lastEff: String? = null,

	@field:SerializedName("reg")
	val reg: String? = null,

	@field:SerializedName("alb")
	val alb: Any? = null,

	@field:SerializedName("sdesc")
	val sdesc: Any? = null,

	@field:SerializedName("btyp")
	val btyp: String? = null,

	@field:SerializedName("admin")
	val admin: Any? = null,

	@field:SerializedName("pit_name")
	val pitName: String? = null,

	@field:SerializedName("begin_eff")
	val beginEff: String? = null,

	@field:SerializedName("bunga")
	val bunga: Any? = null
)
