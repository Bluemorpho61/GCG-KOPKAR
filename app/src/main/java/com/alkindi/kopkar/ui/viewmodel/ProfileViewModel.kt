package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.remote.response.UserProfileImageResponse
import com.alkindi.kopkar.data.remote.response.UserProfileResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _profileDataResponse = MutableLiveData<UserProfileResponse>()
    val profileDataResponse: LiveData<UserProfileResponse> = _profileDataResponse

    private val _userImageResponse = MutableLiveData<UserProfileImageResponse>()
    val userImageResponse: LiveData<UserProfileImageResponse> = _userImageResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()

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


    fun getUserProfileData(username: String) {
        if (username.isEmpty()) {
            Log.e(TAG, "Error: ${Log.ERROR}")
        } else {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val apiService = ApiConfig.getApiService()
                    val data = mapOf(
                        "username" to username.uppercase()
                    )
                    val encodedJson = ApiNetworkingUtils.jsonFormatter(data)
                    val fullUrl = ApiRemoteCode.apiUrlArranger(
                        ApiConfig.BASE_URL_KOPKAR,
                        ApiConfig.API_DEV_CODE_KOPKAR,
                        ApiConfig.WORKSPACE_CODE_KOPKAR,
                        ApiRemoteCode.GET_USER_PROFILE_INFO,
                        encodedJson
                    )
                    val response = apiService.getProfileExt(fullUrl)
                    Log.d(TAG, "Profile Data API Response: $response")
                    Log.d(TAG, "Encoded Json Data: $encodedJson")
                    _profileDataResponse.value = response
                } catch (e: HttpException) {
                    Log.e(TAG, "Error: ${e.message()}")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }


    fun deleteUserSession(username: String) {
        viewModelScope.launch {
            userRepository.deletePersonalData(username)
            userRepository.logout()
        }
    }


    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }
}