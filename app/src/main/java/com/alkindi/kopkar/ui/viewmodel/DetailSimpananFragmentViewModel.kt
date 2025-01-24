package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.remote.response.DetailSimpananResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class DetailSimpananFragmentViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _detailSimpananResponse = MutableLiveData<DetailSimpananResponse>()
    val detailSimpananResponse: LiveData<DetailSimpananResponse> = _detailSimpananResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = userRepository.getSession().asLiveData()

    suspend fun getDetailSimpananData(userID: String) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "user_emp" to userID.uppercase(),
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl = ApiRemoteCode.apiUrlArranger(
                ApiConfig.BASE_URL_KOPKAR,
                ApiConfig.API_DEV_CODE_KOPKAR,
                ApiConfig.WORKSPACE_CODE_KOPKAR,
                ApiRemoteCode.GET_DETAIL_TARIKSIMP_USER,
                encodedData
            )
            val response = apiService.getDetailSimpanan(fullUrl)
            _detailSimpananResponse.value = response

        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
        } finally {
            _isLoading.value = false
        }

    }

    companion object {
        private val TAG = DetailSimpananFragmentViewModel::class.java.simpleName
    }
}