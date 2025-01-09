package com.alkindi.kopkar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.kopkar.data.remote.response.AjukanPinjamanLainResponse
import com.alkindi.kopkar.data.remote.response.AjukanPinjamanResponse
import com.alkindi.kopkar.data.remote.response.HitungAdmPinjamanLainResponse
import com.alkindi.kopkar.data.remote.response.HitungAdmPinjamanResponse
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.ui.activity.NominalPinjamanActivity

class NominalPinjamanViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _hitungAdmResponse = MutableLiveData<HitungAdmPinjamanResponse>()
    val hitungAdmResponse: LiveData<HitungAdmPinjamanResponse> = _hitungAdmResponse

    private val _hitungAdmResponseLain = MutableLiveData<HitungAdmPinjamanLainResponse>()
    val hitungAdmResponseLain: LiveData<HitungAdmPinjamanLainResponse> = _hitungAdmResponseLain

    private val _uploadPengajuanPinjaman = MutableLiveData<AjukanPinjamanResponse>()
    val uploadPengajuanPinjaman: LiveData<AjukanPinjamanResponse> = _uploadPengajuanPinjaman

    private val _uploadPengajuanPinjamanLain = MutableLiveData<AjukanPinjamanLainResponse>()
    val uploadPengajuanPinjamanLain: LiveData<AjukanPinjamanLainResponse> =
        _uploadPengajuanPinjamanLain

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = userRepository.getSession().asLiveData()

    suspend fun hitungAdmPinjaman(
        lon: String, //tipe pinjaman
        am: Any, //jumlah pinjaman
        term: String, //tenor
        mbrid: String, //id user
        sal: Any, //nominal tipe potongan
        pot: Any //potongan pribadi
    ) {
        try {
            _isLoading.value = true

            val apiService = ApiConfig.getApiService()
            val argl = """{
                "lon":"$lon",
                "am":"$am",
                "term":"$term",
                "mbrid":"$mbrid",
                "sal":"$sal",
                "pot":"$pot"
                }""".trimMargin()
            val response = apiService.hitungAdmPinjaman(argl = argl)
            _hitungAdmResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${Log.ERROR} ")
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun hitungAdmPinjamanLain(
        lon: String,
        am: Any,
        jasa: String,
        term: String,
        mbrid: String
    ) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val argl = """{
                "lon":"$lon",
                "am":"$am",
                "jasa":"$jasa",
                "term":"$term",
                "mbrid":"$mbrid"
                }""".trimIndent()
            val response = apiService.hitungAdmPinjamanLain(argl = argl)
            _hitungAdmResponseLain.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to use hitungAdmPinjamanLain function ${e.message.toString()}")
            return
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun ajukanPinjamanLain(
        mbrid: String,
        amount: String,
        term: String,
        docDate: String,
        angsuran: String,
        totalAmount: String,
        pjmCode: String,
        adm: String,
        loanCode: String,
        provisi: String,
        asuransi: String,
        jasa: String,
        simpKhusus: String,
        danaCair: String,
        gaji: String,
        potonganPribadi: String,
        noAtasan: String
    ) {
        try {
            _isLoading.value = true
            val argl = """{
                 "mbrid":"$mbrid",
                "amount":"$amount",
                "term":"$term",
                "doc_date":"$docDate",
                "angsuran":"$angsuran",
                "tot_am":"$totalAmount",
                "loancode":"$loanCode",
                "adm":"$adm",
                "pjm_code":"$pjmCode",
                "provisi":"$provisi",
                "asuransi":"$asuransi",
                "jasa":"$jasa",
                "simp_khusus":"$simpKhusus",
                "dana_cair":"$danaCair",
                "gaji":"$gaji",
                "pot_pribadi":"$potonganPribadi",
                "no_atasan":"$noAtasan"
                }""".trimMargin()
            val apiService = ApiConfig.getApiService()
            val response = apiService.ajukanPinjamanLain(argl = argl)
            _uploadPengajuanPinjamanLain.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to call the function: ${e.message}")
            return
        } finally {
            _isLoading.value = false
        }

    }

    suspend fun ajukanPinjaman(
        mbrid: String,
        amount: String,
        term: String,
        docDate: String,
        angsuran: String,
        totalAmount: String,
        loancode: String,
        adm: String,
        pjmCode: String,
        provisi: String,
        asuransi: String,
        jasa: String,
        maksAngsuran: String,
        batasAngsuran: String,
        simpKhusus: String,
        minimalSimpanan: String,
        danaCair: String,
        saldoPjm: String,
        jasaTotPjm: String,
        gaji: String,
        potonganPribadi: String,
        noAtasan: String,
        dateCair: String?,
        dateBonus: String?
    ) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            if (dateCair.isNullOrEmpty() && dateBonus.isNullOrEmpty()) {
                val argl = """{
                "mbrid":"$mbrid",
                "amount":"$amount",
                "term":"$term",
                "doc_date":"$docDate",
                "angsuran":"$angsuran",
                "tot_am":"$totalAmount",
                "loancode":"$pjmCode",
                "adm":"$adm",
                "pjm_code":"$loancode",
                "provisi":"$provisi",
                "asuransi":"$asuransi",
                "jasa":"$jasa",
                "maksur":"$maksAngsuran",
                "batang":"$batasAngsuran",
                "simp_khusus":"$simpKhusus",
                "minisimp":"$minimalSimpanan",
                "dana_cair":"$danaCair",
                "saldo_pjm":"$saldoPjm",
                "jasa_totpjm":"$jasaTotPjm",
                "gaji":"$gaji",
                "pot_pribadi":"$potonganPribadi",
                "no_atasan":"$noAtasan"
                }""".trimIndent()
                val response = apiService.ajukanPinjaman(argl = argl)
                _uploadPengajuanPinjaman.value = response
                Log.d(TAG, "Data yang dikirim: $argl")
            } else {
                val argl = """{
                "mbrid":"$mbrid",
                "amount":"$amount",
                "term":"$term",
                "doc_date":"$docDate",
                "angsuran":"$angsuran",
                "tot_am":"$totalAmount",
                "loancode":"$loancode",
                "adm":"$adm",
                "pjm_code":"$pjmCode",
                "provisi":"$provisi",
                "asuransi":"$asuransi",
                "jasa":"$jasa",
                "maksur":"$maksAngsuran",
                "batang":"$batasAngsuran",
                "simp_khusus":"$simpKhusus",
                "minisimp":"$minimalSimpanan",
                "dana_cair":"$danaCair",
                "saldo_pjm":"$saldoPjm",
                "jasa_totpjm":"$jasaTotPjm",
                "gaji":"$gaji",
                "pot_pribadi":"$potonganPribadi",
                "no_atasan":"$noAtasan",
                "date_cair":"$dateCair",
                "date_bonus":"$dateBonus"
                }""".trimIndent()
                Log.d(TAG, "Data yang dikirim: $argl")
                val response = apiService.ajukanPinjaman(argl = argl)
                _uploadPengajuanPinjaman.value = response
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error making an API Request: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = NominalPinjamanActivity::class.java.simpleName
    }
}