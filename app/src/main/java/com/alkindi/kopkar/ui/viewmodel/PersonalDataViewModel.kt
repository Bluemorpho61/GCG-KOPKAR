package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.remote.response.EditPersonalDataResponse
import com.alkindi.kopkar.data.remote.response.PersonalDataResponse
import com.alkindi.kopkar.data.remote.response.UserProfileImageResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class PersonalDataViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _personalDataResponse = MutableLiveData<PersonalDataResponse>()
    val personalDataResponse: LiveData<PersonalDataResponse> = _personalDataResponse

    private val _userImageResponse = MutableLiveData<UserProfileImageResponse>()
    val userImageResponse: LiveData<UserProfileImageResponse> = _userImageResponse

    private val _editUserPersonalDataResponse = MutableLiveData<EditPersonalDataResponse>()
    val editPersonalDataResponse: LiveData<EditPersonalDataResponse> = _editUserPersonalDataResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()

    suspend fun getUserPersonalData(userID: String) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "username" to userID.uppercase()
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl = ApiRemoteCode.apiUrlArranger(
                ApiConfig.BASE_URL_KOPKAR,
                ApiConfig.API_DEV_CODE_KOPKAR,
                ApiConfig.WORKSPACE_CODE_KOPKAR,
                ApiRemoteCode.GET_USER_PERSONAL_DATA,
                encodedData
            )
            val response = apiService.getPersonal(fullUrl)
            _personalDataResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to execute getUserPersonalData function: ${e.message.toString()}")
        } finally {
            _isLoading.value = false
        }
    }


    suspend fun getUserImage(mbrid: String, workspace: String) {
        if (mbrid.isEmpty()) {
            Log.e(TAG, "Unable to use function getUserImage: ${Log.ERROR}")
        } else {
            try {
                _isLoading.value = true
                val apiService = ApiConfig.getApiService()
                val data = mapOf(
                    "mbrempno" to mbrid,
                    "workspace" to workspace
                )
                val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                val fullUrl = ApiRemoteCode.apiUrlArranger(
                    ApiConfig.BASE_URL_KOPKAR,
                    ApiConfig.API_DEV_CODE_KOPKAR,
                    ApiConfig.WORKSPACE_CODE_KOPKAR,
                    ApiRemoteCode.GET_USER_IMG,
                    encodedData
                )
                val response = apiService.getImageGambar(fullUrl)
                _userImageResponse.value = response
            } catch (e: Exception) {
                Log.e(TAG, "Unable to execute request image process: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    suspend fun editUserPersonalData(
        mbrid: String,
        name: String?,
        nip: String?,
        birthPlace: String?,
        birthDate: String?,
        address: String
    ) {
        try {
            _isLoading.value = true

            val argl = """{
                "mbrid":"$mbrid",
                "name":"$name",
                "nip":"$nip",
                "birth_place":"$birthPlace",
                "birth_date":"$birthDate",
                "address":"$address"
                }""".trimMargin()
            val apiService = ApiConfig.getApiService()
            val response = apiService.editUserPersonalData(argl = argl)
            _editUserPersonalDataResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Error when executing editUserPersonalData function: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }


//    fun getPersonalData(username: String?) {
//        if (username == null) Log.e(
//            TAG, "Couldn't fetch current user data: " + Log.ERROR.toString()
//        ) else {
//            try {
//                viewModelScope.launch {
//                    _isLoading.value = true
//                    userRepository.fetchUserPersonalData(username)
//                    _isLoading.value = false
//                }
//
//            } catch (e: Exception) {
//                Log.e(TAG, "Error in Viewmodel class: ${e.message.toString()}")
//                _isLoading.value = false
//            }
//        }
//    }


    companion object {
        private val TAG = PersonalDataViewModel::class.java.simpleName
    }
}