package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<UserProfileDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UserProfileDataItem(

	@field:SerializedName("fname")
	val fname: String? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
