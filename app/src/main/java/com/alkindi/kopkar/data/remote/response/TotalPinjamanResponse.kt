package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class TotalPinjamanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataTotalPinjaman? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataTotalPinjaman(

	@field:SerializedName("sum")
	val sum: Int? = null
)
