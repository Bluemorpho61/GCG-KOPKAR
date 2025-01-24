package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.remote.response.RiwayatTarikSimpananResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class RiwayatTransaksiFragmentViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _historyTarikSimpananResponse = MutableLiveData<RiwayatTarikSimpananResponse>()
    val historyTarikSimpResponse: LiveData<RiwayatTarikSimpananResponse> =
        _historyTarikSimpananResponse
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()


    suspend fun getHistoryTarikSimp(username: String) {
        try {
            _isLoading.value = true
            val data = mapOf(
                "user_emp" to username.uppercase()
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl = ApiRemoteCode.apiUrlArranger(
                ApiConfig.BASE_URL_KOPKAR,
                ApiConfig.API_DEV_CODE_KOPKAR,
                ApiConfig.WORKSPACE_CODE_KOPKAR,
                ApiRemoteCode.GET_HISTORY_TARIKSIMP_USER,
                encodedData
            )
            val apiService = ApiConfig.getApiService()
            val responds = apiService.getRiwayatTarikSimp(fullUrl)
            _historyTarikSimpananResponse.value = responds
            Log.d(TAG, "API Response: $responds")
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching the data: ${e.message.toString()}")
        } finally {
            _isLoading.value = false
        }


    }

    companion object {
        private val TAG = TarikSimpananViewModel::class.java.simpleName
    }
}