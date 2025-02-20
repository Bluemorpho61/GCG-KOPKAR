package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileImageResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
