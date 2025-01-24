package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.remote.response.AjukanPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.ui.activity.NominalPinjamanActivity

class NominalPinjamanViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uploadPengajuanPinjaman = MutableLiveData<AjukanPinjamanResponse>()
    val uploadPengajuanPinjaman: LiveData<AjukanPinjamanResponse> = _uploadPengajuanPinjaman

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = userRepository.getSession().asLiveData()


    suspend fun ajukanPinjaman(
        mbrid: String,
        amount: String,
        term: String,
        loancode: String,
        amjasa: String,
        aminst: String,
        amp: String,
        intAdm: String,
        amAdm: String,
        totAm: String,
        plafon: String
    ) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val argl = """{
                "mbrempno":"$mbrid",
                "loancode":"$loancode",
                "nom_pinjaman":"$amount",
                "tenor":"$term",
                "amjasa":"$amjasa",
                "aminst":"$aminst",
                "amp":"$amp",
                "int_adm":"$intAdm",
                "am_adm":"$amAdm",
                "totam":"$totAm",
                "plafon":"$plafon"
            }""".trimIndent()
            val response = apiService.ajukanPinjaman(argl = argl)
            _uploadPengajuanPinjaman.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to execute function: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = NominalPinjamanActivity::class.java.simpleName
    }
}