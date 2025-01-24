package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkindi.kopkar.data.remote.response.TarikSimpananProcessedResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class TarikSimpananProcessedViewModel(userRepository: UserRepository) : ViewModel() {
    private val _tarikSimpananProcessed = MutableLiveData<TarikSimpananProcessedResponse>()
    val tarikSimpananProcessed: LiveData<TarikSimpananProcessedResponse> = _tarikSimpananProcessed

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun getPullTransactionInfo(docnum: String) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "docnum" to docnum
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl = ApiRemoteCode.apiUrlArranger(
                ApiConfig.BASE_URL_KOPKAR,
                ApiConfig.API_DEV_CODE_KOPKAR,
                ApiConfig.WORKSPACE_CODE_KOPKAR,
                ApiRemoteCode.GET_DETAIL_PENARIKAN_SIMPANAN,
                encodedData
            )
            val response = apiService.getTarikSimpananInfo(fullUrl)
            _tarikSimpananProcessed.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to execute getPullTransactionInfo: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }


    companion object {
        private val TAG = TarikSimpananProcessedViewModel::class.java.simpleName
    }
}