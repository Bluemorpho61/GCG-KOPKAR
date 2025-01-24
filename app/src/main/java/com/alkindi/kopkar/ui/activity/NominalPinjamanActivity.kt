package com.alkindi.kopkar.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.alkindi.kopkar.data.local.model.ProcessedCalculation
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.databinding.ActivityNominalPinjamanBinding
import com.alkindi.kopkar.ui.viewmodel.NominalPinjamanViewModel
import com.alkindi.kopkar.utils.AndroidUIHelper
import com.alkindi.kopkar.utils.FormatterAngka
import kotlinx.coroutines.launch

class NominalPinjamanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNominalPinjamanBinding
    private lateinit var userID: String
    private var docnumPinjaman: String? = null
    private var processedCalculation: ProcessedCalculation? = null
    private var danaDiterima: Int? = null
    private val nominalPinjamanViewModel: NominalPinjamanViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNominalPinjamanBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSession()
        getDataFromPreviousActivity()
        checkLoading()
        observeResponsePengajuan()
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnConfirmPinjaman.setOnClickListener {
            unggahDataPengajuanPinjaman()
        }

    }

    private fun checkLoading() {
        nominalPinjamanViewModel.isLoading.observe(this) { res ->
            showLoading(res)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun getSession() {
        nominalPinjamanViewModel.getSession().observe(this) {
            userID = it.username
        }
    }


    private fun getDataFromPreviousActivity() {
        @Suppress("DEPRECATION", "DEPRECATION") val pinjamanData =
            intent.getParcelableExtra<ProcessedCalculation>("extra_data")

        processedCalculation = pinjamanData

        val jasaBln = pinjamanData?.amp
        val biayaAdmin = pinjamanData?.intadm
        val angsBln = pinjamanData?.aminst
        val tenor = pinjamanData?.tenor
        val nominalPinjaman =
            FormatterAngka.formatterRibuanKeInt(pinjamanData?.nominalPinjaman.toString())
        val tipePotongan = pinjamanData?.tipePotongan

        this.danaDiterima = nominalPinjaman

        binding.edtJasaBln.setText(jasaBln)
        binding.edtAdministrasi.setText(biayaAdmin)
        binding.edtDanaDiterima.setText(FormatterAngka.formatterNilaiUangIndonesia(this.danaDiterima!!.toDouble()))
        binding.tvTenorPinjaman.text = tenor
        binding.tvTipePinjaman.text = tipePotongan
        binding.imTipePinjaman.setText(tipePotongan!!)
        binding.edtAngsBln.setText(
            FormatterAngka.formatterNilaiUangIndonesia(
                angsBln!!.toDouble()
            )
        )


    }

    private fun observeResponsePengajuan() {
        nominalPinjamanViewModel.uploadPengajuanPinjaman.observe(this) { res ->
            if (res.code == 200) {
                docnumPinjaman = res.data!!.docNum
                val toDetailPinjaman = Intent(this, DetailPinjamanInfoActivity::class.java).apply {
                    putExtra(DetailPinjamanInfoActivity.EXTRA_DOCNUM, docnumPinjaman)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
                AndroidUIHelper.showWarningToastShort(this, "Pengajuan pinjaman berhasil!")
                startActivity(toDetailPinjaman)
                finish()
            } else {
                Log.e(TAG, "Unable to processs transaction: ${res.message}")
                AndroidUIHelper.showWarningToastShort(
                    this,
                    "Error: ${res.code}: ${res.message.toString()}"
                )
                return@observe
            }
        }
    }

    private fun unggahDataPengajuanPinjaman() {
        val nominalPinjaman = danaDiterima
        val tenorPinjaman = binding.tvTenorPinjaman.text.toString()
        val tipePinjaman = binding.tvTipePinjaman.text.toString()

        lifecycleScope.launch {
            nominalPinjamanViewModel.ajukanPinjaman(
                userID,
                amount = nominalPinjaman.toString(),
                term = tenorPinjaman,
                loancode = tipePinjaman,
                amjasa = processedCalculation?.amjasa.toString(),
                amAdm = processedCalculation?.amAdm.toString(),
                aminst = processedCalculation?.aminst.toString(),
                amp = processedCalculation?.amp.toString(),
                totAm = processedCalculation?.totam.toString(),
                plafon = processedCalculation?.plafon.toString(),
                intAdm = processedCalculation?.intadm.toString()
            )
        }

    }

    companion object {
        private val TAG = NominalPinjamanActivity::class.java.simpleName
        const val EXTRA_DATA = "extra_data"
    }
}




