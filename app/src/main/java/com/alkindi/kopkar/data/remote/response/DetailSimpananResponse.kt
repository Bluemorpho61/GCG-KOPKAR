package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailSimpananResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DetailSimpananItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DetailSimpananItem(

	@field:SerializedName("createdinfo")
	val createdinfo: String? = null,

	@field:SerializedName("trans_date")
	val transDate: String? = null,

	@field:SerializedName("amount")
	val amount: Any? = null,

	@field:SerializedName("pstatus")
	val pstatus: String? = null,

	@field:SerializedName("sdesc")
	val sdesc: Any? = null,

	@field:SerializedName("nipp")
	val nipp: Any? = null,

	@field:SerializedName("doc_number")
	val docNumber: String? = null,

	@field:SerializedName("currs")
	val currs: String? = null,

	@field:SerializedName("docref_ups")
	val docrefUps: Any? = null,

	@field:SerializedName("txn_code")
	val txnCode: String? = null,

	@field:SerializedName("mbr_mbrid")
	val mbrMbrid: String? = null,

	@field:SerializedName("mbr_company")
	val mbrCompany: String? = null,

	@field:SerializedName("doc_date")
	val docDate: String? = null,

	@field:SerializedName("modifiedinfo")
	val modifiedinfo: Any? = null,

	@field:SerializedName("paytype")
	val paytype: String? = null
)
