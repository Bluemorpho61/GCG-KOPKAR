package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkindi.kopkar.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class DetailPinjamanInfoViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _detailPinjamanInfoResponse = MutableLiveData<DetailHistoryPinjamanResponse>()
    val detailHistoryPinjamanInfoResponse: LiveData<DetailHistoryPinjamanResponse> =
        _detailPinjamanInfoResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getDetailPinjamanInfo(docNum: String) {
        if (docNum.isEmpty()) {
            Log.e(TAG, "Tidak ada nilai yang dikirim pada parameter")
            return
        } else {
            try {
                _isLoading.value = true
                val apiService = ApiConfig.getApiService()
                val apiCode =
                    "UQihbFj9rzSMdr02uS94Z1oDhH69zUlWP9Zwm1Tdxg2Gye0yFDZXOY5AHiEp%2BuOwUA0E9KRDqbA%3D"
                val data = mapOf(
                    "docNum" to docNum
                )
                val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                val fullUrl =
                    "${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${apiCode};vars=${encodedData}"
                val response = apiService.getDetailHistoryPinjaman(fullUrl)
                _detailPinjamanInfoResponse.value = response
            } catch (e: Exception) {
                Log.e(TAG, "Failed to execute getDetailPinjamanInfo ${e.message}")
                return
            }
        }
    }

    companion object {
        private val TAG = DetailPinjamanInfoViewModel::class.java.simpleName
    }
}