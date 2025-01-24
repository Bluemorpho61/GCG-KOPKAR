package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.kopkar.data.remote.response.HitungAdmPinjamanResponse
import com.alkindi.kopkar.data.remote.response.JenisPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.remote.retrofit.ApiRemoteCode
import com.alkindi.kopkar.data.repository.UserRepository
import kotlinx.coroutines.launch

class PinjamanViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _listPotonganResponse = MutableLiveData<JenisPinjamanResponse>()
    val listPotonganResponse: LiveData<JenisPinjamanResponse> = _listPotonganResponse

    private val _hitungAdmResponse = MutableLiveData<HitungAdmPinjamanResponse>()
    val hitungAdmResponse: LiveData<HitungAdmPinjamanResponse> = _hitungAdmResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = userRepository.getSession().asLiveData()

    init {
        viewModelScope.launch {
            getTipePotonganList()
        }
    }

    private suspend fun getTipePotonganList() {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val fullUrl = ApiRemoteCode.apiUrlArranger(
                ApiConfig.BASE_URL_KOPKAR,
                ApiConfig.API_DEV_CODE_KOPKAR,
                ApiConfig.WORKSPACE_CODE_KOPKAR,
                ApiRemoteCode.GET_LIST_TIPE_PINJAMAN,
                null
            )
            val response = apiService.getJenisPinjamanList(fullUrl)
            _listPotonganResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Failed to call the API ${e.message}")
        } finally {
            _isLoading.value = false
        }

    }

    suspend fun hitungAdmPinjaman(
        lon: String,
        am: Any,
        term: String,

    ) {
        try {
            _isLoading.value = true

            val apiService = ApiConfig.getApiService()
            val argl = """{
                "lon":"$lon",
                "am":"$am",
                "term":"$term"
                }""".trimMargin()
            val response = apiService.hitungAdmPinjaman(argl = argl)
            _hitungAdmResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message} ")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = PinjamanViewModel::class.java.simpleName
    }
}