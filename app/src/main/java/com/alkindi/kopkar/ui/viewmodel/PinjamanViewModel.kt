package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.remote.response.TenorListResponse
import com.alkindi.kopkar.data.remote.response.TipePotonganResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils

class PinjamanViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _listPotonganResponse = MutableLiveData<TipePotonganResponse>()
    val listPotonganResponse: LiveData<TipePotonganResponse> = _listPotonganResponse

    private val _listTenor = MutableLiveData<TenorListResponse>()
    val listTenor: LiveData<TenorListResponse> = _listTenor

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = userRepository.getSession().asLiveData()

    suspend fun getTipePotonganList(tipePinjaman: String) {
        if (tipePinjaman.isEmpty()) {
            Log.e(TAG, "Parameter 'tipePinjaman' isn't receive any value: ${Log.ERROR}")
        } else {
            try {
                _isLoading.value = true
                val apiService = ApiConfig.getApiService()
                val data = mapOf(
                    "tipepotongan" to tipePinjaman
                )
                val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                val apiCode = "tBuYtyWkt9BVca9T0hdoheGNqUKSfmxc0OeVrYJV618%3D"
                val fullUrl =
                    "${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${apiCode};vars=${encodedData}"
                val response = apiService.getTipePotonganUser(fullUrl)
                _listPotonganResponse.value = response
            } catch (e: Exception) {
                Log.e(TAG, "Failed to call the API ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun getTenorList(tipePinjaman: String) {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "tipePinjaman" to tipePinjaman
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl =
                "${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=tBuYtyWkt9DJpiePfo46tA3TDdHcBBda/t0TnlvsUHGFBHt4quLErXfLmMJrecF6;vars=${encodedData}"
            val response = apiService.getTenorList(fullUrl)
            _listTenor.value = response
        } catch (e: Exception) {
            Log.e(TAG, "An error occured: ${Log.ERROR}")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = PinjamanViewModel::class.java.simpleName
    }
}