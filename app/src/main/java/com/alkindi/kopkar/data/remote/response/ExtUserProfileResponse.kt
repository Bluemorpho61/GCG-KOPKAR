package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class ExtUserProfileResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: UserProfileItem? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UserProfileItem(

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("ulock")
	val ulock: Any? = null,

	@field:SerializedName("dbuser")
	val dbuser: String? = null,

	@field:SerializedName("dbspid")
	val dbspid: Any? = null,

	@field:SerializedName("valid_from")
	val validFrom: Any? = null,

	@field:SerializedName("dec_sep")
	val decSep: Any? = null,

	@field:SerializedName("dbsandi")
	val dbsandi: Any? = null,

	@field:SerializedName("modify_by")
	val modifyBy: Any? = null,

	@field:SerializedName("mname")
	val mname: Any? = null,

	@field:SerializedName("title")
	val title: Any? = null,

	@field:SerializedName("building")
	val building: Any? = null,

	@field:SerializedName("user_theme")
	val userTheme: Any? = null,

	@field:SerializedName("create_by")
	val createBy: String? = null,

	@field:SerializedName("lname")
	val lname: Any? = null,

	@field:SerializedName("dbcode")
	val dbcode: String? = null,

	@field:SerializedName("dbdate")
	val dbdate: Any? = null,

	@field:SerializedName("crd")
	val crd: String? = null,

	@field:SerializedName("apicode")
	val apicode: Any? = null,

	@field:SerializedName("function")
	val function: Any? = null,

	@field:SerializedName("valid_to")
	val validTo: Any? = null,

	@field:SerializedName("client")
	val client: String? = null,

	@field:SerializedName("alias")
	val alias: Any? = null,

	@field:SerializedName("create_date")
	val createDate: String? = null,

	@field:SerializedName("floor")
	val floor: Any? = null,

	@field:SerializedName("fax")
	val fax: Any? = null,

	@field:SerializedName("lang")
	val lang: Any? = null,

	@field:SerializedName("telpext")
	val telpext: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("comp")
	val comp: String? = null,

	@field:SerializedName("fname")
	val fname: String? = null,

	@field:SerializedName("empid")
	val empid: Any? = null,

	@field:SerializedName("dep_id")
	val depId: Any? = null,

	@field:SerializedName("time_format")
	val timeFormat: Any? = null,

	@field:SerializedName("parea")
	val parea: Any? = null,

	@field:SerializedName("user_image")
	val userImage: Any? = null,

	@field:SerializedName("dep_name")
	val depName: Any? = null,

	@field:SerializedName("ugroup")
	val ugroup: Any? = null,

	@field:SerializedName("room")
	val room: Any? = null,

	@field:SerializedName("dbpswd")
	val dbpswd: Any? = null,

	@field:SerializedName("thousand_sep")
	val thousandSep: Any? = null,

	@field:SerializedName("dig_after_dec")
	val digAfterDec: Any? = null,

	@field:SerializedName("date_format")
	val dateFormat: Any? = null,

	@field:SerializedName("email_flag")
	val emailFlag: Any? = null,

	@field:SerializedName("modify_date")
	val modifyDate: Any? = null,

	@field:SerializedName("dbsect")
	val dbsect: String? = null
)
