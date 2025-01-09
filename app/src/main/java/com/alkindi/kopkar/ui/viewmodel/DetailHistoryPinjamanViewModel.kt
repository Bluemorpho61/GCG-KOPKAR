package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkindi.kopkar.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
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
                    val apiCode =
                        "UQihbFj9rzSMdr02uS94Z1oDhH69zUlWP9Zwm1Tdxg2Gye0yFDZXOY5AHiEp%2BuOwUA0E9KRDqbA%3D"
                    val data = mapOf(
                        "docNum" to docnum
                    )
                    val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                    val fullUrl =
                        "${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${apiCode};vars=${encodedData}"
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