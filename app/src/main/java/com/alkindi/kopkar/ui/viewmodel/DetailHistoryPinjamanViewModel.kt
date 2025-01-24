package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkindi.kopkar.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils
import kotlinx.coroutines.launch

class DetailHistoryPinjamanViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _detailHistoryPinjamanResponse = MutableLiveData<DetailHistoryPinjamanResponse>()
    val detailHistoryPinjamanResponse: LiveData<DetailHistoryPinjamanResponse> =
        _detailHistoryPinjamanResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailHistoryPinjaman(docnum: String) {
        if (docnum.isEmpty()) {
            Log.e(TAG, "Function doesn't receive the docnum value: ${Log.ERROR}")
        } else {
            try {
                _isLoading.value = true
                viewModelScope.launch {
                    val apiService = ApiConfig.getApiService()
                    val data = mapOf(
                        "docNum" to docnum
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
                    _detailHistoryPinjamanResponse.value = response

                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        private val TAG = DetailHistoryPinjamanViewModel::class.java.simpleName
    }
}