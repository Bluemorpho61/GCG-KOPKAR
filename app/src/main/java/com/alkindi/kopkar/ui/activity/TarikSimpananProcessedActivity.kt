package com.alkindi.kopkar.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.alkindi.kopkar.data.local.model.ProcessedTarikSimp
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.databinding.ActivityTarikSimpananProcessedBinding
import com.alkindi.kopkar.ui.viewmodel.TarikSimpananProcessedViewModel
import com.alkindi.kopkar.utils.AndroidUIHelper
import com.alkindi.kopkar.utils.FormatterAngka
import kotlinx.coroutines.launch

class TarikSimpananProcessedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTarikSimpananProcessedBinding
    private val tarikSimpananProcessedViewModel: TarikSimpananProcessedViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarikSimpananProcessedBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnToHome.setOnClickListener {
            val intent = Intent(this@TarikSimpananProcessedActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        getDataFromPreviousActivity()
    }

    private fun getDataFromPreviousActivity() {
        val tarikSimpInfo = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(
                EXTRA_PROCESSED_TARIK_SIMP,
                ProcessedTarikSimp::class.java
            )
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PROCESSED_TARIK_SIMP)
        }

        if (tarikSimpInfo == null) {
            AndroidUIHelper.showAlertDialog(
                this,
                "Error",
                "Tidak dapat mengambil info data dari transaksi saat ini"
            )
            Log.e(TAG, "Tidak dapat mengambil data tarik simpanan dari activity sebelumnya")
            finish()
            return
        }
        fetchPullTransactionInfo(tarikSimpInfo.docnum!!)
        observeFetchedData()
    }

    private fun observeFetchedData() {
        tarikSimpananProcessedViewModel.tarikSimpananProcessed.observe(this) { res ->
            val nominalPenarikanSimpanan = res.data?.amount.toString()

            binding.tvTipeSimpanan.text = "Simpanan Sukarela"
            binding.tvDocnum.text = res.data?.docNum
            binding.tvNominalYangDitarik.text =
                FormatterAngka.formatterNilaiUangIndonesia(nominalPenarikanSimpanan.toDouble())
            binding.tvTglTransaksi.text = res.data?.transDate.toString()
        }
    }

    private fun fetchPullTransactionInfo(docnum: String) {
        lifecycleScope.launch {
            tarikSimpananProcessedViewModel.getPullTransactionInfo(docnum)
        }
    }

    companion object {
        private val TAG = TarikSimpananProcessedActivity::class.java.simpleName
        const val EXTRA_PROCESSED_TARIK_SIMP = "extra_processed_tarik_simp"
    }
}