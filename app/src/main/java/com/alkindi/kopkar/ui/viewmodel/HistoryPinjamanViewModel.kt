package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.remote.response.HistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class HistoryPinjamanViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _historyPinjamanResponse = MutableLiveData<HistoryPinjamanResponse>()
    val historyPinjamanResponse: LiveData<HistoryPinjamanResponse> = _historyPinjamanResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getHistoryData(userId: String) {

        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "user_id" to userId
            )
            val apiCode = "UQihbFj9rzSMdr02uS94Z1oDhH69zUlWBxxyo8ewHNB3y5jCa3nBeg%3D%3D"
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl =
                "${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${apiCode};vars=${encodedData}"
            val response = apiService.getHistoryPinjaman(fullUrl)
            _historyPinjamanResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
        } finally {
            _isLoading.value = false
        }

    }

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()

    companion object {
        private val TAG = HistoryPinjamanViewModel::class.java.simpleName
    }
}