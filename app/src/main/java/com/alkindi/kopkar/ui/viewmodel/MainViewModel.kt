package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.remote.response.LoginResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    Get saved user login data to used it as session
    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()

    fun signInUser(username: String, password: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.login(ApiConfig.WORKSPACE_CODE_KOPKAR, username, password, "", "")
                _loginResponse.value = successResponse
            } catch (e: HttpException) {
                Log.e(TAG, "Error making API Request: ${e.message()}")
            }catch (e: Exception){
                Log.e(TAG, "General Error: ${e.message}")
            }finally {
                _isLoading.value =false
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}


