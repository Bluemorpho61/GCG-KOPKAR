package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class RiwayatTransaksiResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<RiwayatTransaksiItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class RiwayatTransaksiItem(

	@field:SerializedName("aprinfo")
	val aprinfo: String? = null,

	@field:SerializedName("doc_num")
	val docNum: String? = null,

	@field:SerializedName("doc_date")
	val docDate: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("source")
	val source: String? = null
)
