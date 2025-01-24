package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.remote.response.RiwayatTransaksiResponse
import com.alkindi.kopkar.data.remote.response.TotalPinjamanResponse
import com.alkindi.kopkar.data.remote.response.UserProfileImageResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _totalPinjamanResponse = MutableLiveData<TotalPinjamanResponse>()
    val totalPinjamanResponse: LiveData<TotalPinjamanResponse> = _totalPinjamanResponse

    private val _riwayatTransaksiResponse = MutableLiveData<RiwayatTransaksiResponse>()
    val riwayatTransaksiResponse: LiveData<RiwayatTransaksiResponse> = _riwayatTransaksiResponse

    private val _userImageResponse = MutableLiveData<UserProfileImageResponse>()
    val userImageResponse: LiveData<UserProfileImageResponse> = _userImageResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun checkSavedLoginData() = userRepository.checkSavedPreference()

    fun getSession() = userRepository.getSession().asLiveData()

    suspend fun getTotalPinjaman(userId: String) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "user_id" to userId
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl = ApiRemoteCode.apiUrlArranger(
                ApiConfig.BASE_URL_KOPKAR,
                ApiConfig.API_DEV_CODE_KOPKAR,
                ApiConfig.WORKSPACE_CODE_KOPKAR,
                ApiRemoteCode.GET_TOTALPINJAMAN_USER,
                encodedData
            )
            val response = apiService.getTotalPinjamanAmount(fullUrl)
            _totalPinjamanResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to execute the getTotalPinjaman function: ${e.message.toString()}")
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

    suspend fun getRiwayatTransaksi(userId: String) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "user_id" to userId
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl = ApiRemoteCode.apiUrlArranger(
                ApiConfig.BASE_URL_KOPKAR,
                ApiConfig.API_DEV_CODE_KOPKAR,
                ApiConfig.WORKSPACE_CODE_KOPKAR,
                ApiRemoteCode.GET_RIWAYAT_TRANSAKSI_HOME,
                encodedData
            )
            val response = apiService.getRiwayatTransaksi(fullUrl)
            _riwayatTransaksiResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to call the function getRiwayatTransaksi: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}