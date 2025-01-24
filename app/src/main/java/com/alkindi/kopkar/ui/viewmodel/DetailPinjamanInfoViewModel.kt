package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkindi.kopkar.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
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
                val data = mapOf(
                    "docNum" to docNum
                )
                val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                val fullUrl = ApiRemoteCode.apiUrlArranger(
                    ApiConfig.BASE_URL_KOPKAR,
                    ApiConfig.API_DEV_CODE_KOPKAR,
                    ApiConfig.WORKSPACE_CODE_KOPKAR,
                    ApiRemoteCode.GET_DETAIL_HISTORY_PINJAMAN,
                    encodedData
                )
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