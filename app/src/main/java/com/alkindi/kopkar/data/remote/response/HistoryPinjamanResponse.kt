package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryPinjamanResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<HistoryPinjamanItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class HistoryPinjamanItem(

	@field:SerializedName("loan_code")
	val loanCode: String? = null,

	@field:SerializedName("stl_doc_num")
	val stlDocNum: Any? = null,

	@field:SerializedName("createdinfo")
	val createdinfo: Any? = null,

	@field:SerializedName("instal")
	val instal: Any? = null,

	@field:SerializedName("inct_code")
	val inctCode: Any? = null,

	@field:SerializedName("plafon_am")
	val plafonAm: Any? = null,

	@field:SerializedName("aprinfo")
	val aprinfo: Any? = null,

	@field:SerializedName("stldesc")
	val stldesc: Any? = null,

	@field:SerializedName("sisa_tenor")
	val sisaTenor: Any? = null,

	@field:SerializedName("doc_num")
	val docNum: String? = null,

	@field:SerializedName("apr_stl_by")
	val aprStlBy: Any? = null,

	@field:SerializedName("int_am")
	val intAm: Any? = null,

	@field:SerializedName("settlement")
	val settlement: Any? = null,

	@field:SerializedName("interest")
	val interest: Any? = null,

	@field:SerializedName("mbr_company")
	val mbrCompany: String? = null,

	@field:SerializedName("tot_am")
	val totAm: Any? = null,

	@field:SerializedName("proid")
	val proid: Any? = null,

	@field:SerializedName("settlement_fee")
	val settlementFee: Any? = null,

	@field:SerializedName("settlement_by")
	val settlementBy: Any? = null,

	@field:SerializedName("term")
	val term: Any? = null,

	@field:SerializedName("date_cair")
	val dateCair: Any? = null,

	@field:SerializedName("pay_type")
	val payType: Any? = null,

	@field:SerializedName("loan_name")
	val loanName: String? = null,

	@field:SerializedName("accum")
	val accum: Int? = null,

	@field:SerializedName("evm")
	val evm: Any? = null,

	@field:SerializedName("amount")
	val amount: Any? = null,

	@field:SerializedName("apr")
	val apr: String? = null,

	@field:SerializedName("settlement_date")
	val settlementDate: Any? = null,

	@field:SerializedName("settlement_pokok")
	val settlementPokok: Any? = null,

	@field:SerializedName("am_adm")
	val amAdm: Any? = null,

	@field:SerializedName("int_type")
	val intType: Any? = null,

	@field:SerializedName("remain")
	val remain: Any? = null,

	@field:SerializedName("int_adm")
	val intAdm: Any? = null,

	@field:SerializedName("evy")
	val evy: Any? = null,

	@field:SerializedName("svm")
	val svm: Any? = null,

	@field:SerializedName("mbr_mbrid")
	val mbrMbrid: String? = null,

	@field:SerializedName("lon_desc")
	val lonDesc: Any? = null,

	@field:SerializedName("jasa")
	val jasa: Any? = null,

	@field:SerializedName("mbr_name")
	val mbrName: String? = null,

	@field:SerializedName("pokok")
	val pokok: Any? = null,

	@field:SerializedName("doc_date")
	val docDate: String? = null,

	@field:SerializedName("svy")
	val svy: Any? = null,

	@field:SerializedName("modifiedinfo")
	val modifiedinfo: Any? = null,

	@field:SerializedName("apr_stl_date")
	val aprStlDate: Any? = null,

	@field:SerializedName("settlement_amount")
	val settlementAmount: Any? = null,

	@field:SerializedName("curency")
	val curency: Any? = null,

	@field:SerializedName("stl_apr_status")
	val stlAprStatus: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)
