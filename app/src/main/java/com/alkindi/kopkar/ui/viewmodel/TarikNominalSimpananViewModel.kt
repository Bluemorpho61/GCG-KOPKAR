package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.remote.response.TarikNominalSimpananResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import kotlinx.coroutines.launch

class TarikNominalSimpananViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _tarikNominalSimpananResponse = MutableLiveData<TarikNominalSimpananResponse>()
    val tarikNominalSimpananResponse: LiveData<TarikNominalSimpananResponse> =
        _tarikNominalSimpananResponse
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()


    fun tarikNominalSimpanan(
        mbrid: String,
        nominalSimpanan: String,
        catatan: String?,
        tipeSimpanan: String,
        simpananYgTersedia: String,
        transDate: String
    ) {
        if (nominalSimpanan.isEmpty() && tipeSimpanan.isEmpty()) {
            Log.e(TAG, "Failed to make a tarik simpnanan request")
            return
        } else {
            val argl = """{
                    "mbrid":"$mbrid",
                    "trans_date":"$transDate",
                    "amount":"$nominalSimpanan",
                    "doc_date":"$transDate",
                    "stp":"$tipeSimpanan",
                    "txn_amount":"$simpananYgTersedia",
                    "ket":"$catatan"
                    }""".trimIndent()
            try {
                _isLoading.value = true

//                val responseCode =
//                    "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6y6OVw0Q2/ZWSE2T%2BDBSLsen/SgBttLGZS"
//                "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=${responseCode}"
                Log.d(
                    TAG,
                    "Data yang dimasukkan: (MBRID: $mbrid, nominal yg ditarik: $nominalSimpanan, catatan: $catatan, tipe simpanan: $tipeSimpanan, simpanan yang tersedia: $simpananYgTersedia, transDate: $transDate)"
                )
                viewModelScope.launch {

                    val response = ApiConfig.getApiService().postTarikSimpanan(argl = argl)
                    _tarikNominalSimpananResponse.value = response
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to make a tarik simpnanan request ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        private val TAG = TarikNominalSimpananViewModel::class.java.simpleName
    }
}