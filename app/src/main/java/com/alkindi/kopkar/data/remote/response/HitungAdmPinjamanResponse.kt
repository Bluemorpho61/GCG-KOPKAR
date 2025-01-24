package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class HitungAdmPinjamanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: SmtData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Smt(

	@field:SerializedName("totam")
	val totam: Any? = null,

	@field:SerializedName("amp")
	val amp: Any? = null,

	@field:SerializedName("aminst")
	val aminst: Any? = null,

	@field:SerializedName("amjasa")
	val amjasa: Any? = null,

	@field:SerializedName("amadm")
	val amadm: Any? = null,

	@field:SerializedName("intadm")
	val intadm: Any? = null,

	@field:SerializedName("plafon")
	val plafon: Any? = null
)

data class SmtData(

	@field:SerializedName("smt")
	val smt: Smt? = null
)
