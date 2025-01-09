package com.alkindi.kopkar.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: UpdatedData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UpdatedData(

	@field:SerializedName("updatedFields")
	val updatedFields: List<String?>? = null,

	@field:SerializedName("userID")
	val userID: String? = null
)
