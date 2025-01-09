package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryTarikSimpResponse(
    @SerializedName("doc_num") val docNum: String? = "Kosong",
    @SerializedName("mbr_company") val mbrCompany: String? = "Kosong",
    @SerializedName("mbr_mbrid") val mbrMbrid: String? = "Kosong",
    @SerializedName("trans_date") val transDate: String? = "Kosong",
    @SerializedName("amount") val amount: String? = "Kosong",
    @SerializedName("tgl_cair") val tglCair: String? = "Kosong",
    @SerializedName("txn_type") val txnType: String? = "Kosong",
    @SerializedName("ket") val ket: String? = "Kosong",
    @SerializedName("createdinfo") val createdInfo: String? = "Kosong",
    @SerializedName("modifiedinfo") val modifiedInfo: String? = "Kosong",
    @SerializedName("doc_date") val docDate: String? = "Kosong",
    @SerializedName("aprinfo") val apr: String? = "Kosong",
    @SerializedName("apr_by") val aprBy: String? = "Kosong",
    @SerializedName("apr_date") val aprDate: String? = "Kosong",
    @SerializedName("stp") val stp: String? = "Kosong",
    @SerializedName("txn_amount") val txnAmount: String? = "Kosong",
    @SerializedName("aprinfo") val aprInfo: String? = "Kosong",
    @SerializedName("inct_code") val inctCode: String? = "Kosong",
    @SerializedName("pay_type") val payType: String? = "Kosong",
    @SerializedName("status") val status: String? = "Kosong"
)