package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class AjukanPinjamanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataPengajuanPinjaman? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataPengajuanPinjaman(

	@field:SerializedName("trans_date")
	val transDate: String? = null,

	@field:SerializedName("doc_num")
	val docNum: String? = null,

	@field:SerializedName("doc_date")
	val docDate: String? = null
)
