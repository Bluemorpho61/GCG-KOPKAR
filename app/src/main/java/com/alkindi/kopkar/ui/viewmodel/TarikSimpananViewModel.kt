package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.remote.response.NominalSimpananResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.ApiNetworkingUtils
import kotlinx.coroutines.launch

class TarikSimpananViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _dataSimpananUser = MutableLiveData<NominalSimpananResponse>()
    val dataSimpananUser: LiveData<NominalSimpananResponse> = _dataSimpananUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()
    

    fun getDataSimpananUser(username: String) {
        if (username.isEmpty()) {
            Log.e(TAG, "Couln't get the data because the user id is null")
        } else {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val responseCode =
                        "NU5mgOhAZUE%2Bb0mTYNGiy8FnGKoc3rg3V1Q9KP8l2D9Qwl%2Bjv4wJfA%3D%3D"
                    val data = mapOf(
                        "username" to username.uppercase()
                    )
                    val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                    val fullUrl =
                        "${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${responseCode};vars=${encodedData}"
                    val apiService = ApiConfig.getApiService()
                    val response = apiService.getNominalSimpananUser(fullUrl)
                    _dataSimpananUser.value = response
                } catch (e: Exception) {
                    Log.e(TAG, "Eror fetching the data ${e.message.toString()}")
                } finally {
                    _isLoading.value = false
                }

            }
        }
    }

    companion object {
        private val TAG = TarikSimpananViewModel::class.java.simpleName
    }
}