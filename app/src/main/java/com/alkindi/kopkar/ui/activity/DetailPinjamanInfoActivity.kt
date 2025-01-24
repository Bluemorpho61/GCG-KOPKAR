package com.alkindi.kopkar.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.databinding.ActivityDetailPinjamanInfoBinding
import com.alkindi.kopkar.ui.viewmodel.DetailPinjamanInfoViewModel
import com.alkindi.kopkar.utils.FormatterAngka
import kotlinx.coroutines.launch

class DetailPinjamanInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPinjamanInfoBinding
    private lateinit var extraDocnum: String
    private val detailPinjamanInfoViewModel: DetailPinjamanInfoViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPinjamanInfoBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        extraDocnum = intent.getStringExtra(EXTRA_DOCNUM).toString()

        fetchData()
        showData()
    }

    private fun showData() {
        detailPinjamanInfoViewModel.detailHistoryPinjamanInfoResponse.observe(this) { res ->
            val tgl = res.data?.get(0)?.docDate.toString()
            val nominalPinjaman = res.data?.get(0)?.amount.toString()
            val nominalAdminstrasi = res.data?.get(0)?.amAdm.toString()
            val jumlahTenor = res.data?.get(0)?.term.toString()
            val tipePotongan = res.data?.get(0)?.loanCode.toString()
            val persenanAdmin = res.data?.get(0)?.intAdm.toString()
            val parsedTgl = FormatterAngka.dateFormatForDetail(tgl)
            val parsedNominalPinjaman =
                FormatterAngka.formatterAngkaRibuanDouble(nominalPinjaman.toDouble())
            val parsedNominalJasa =
                FormatterAngka.formatterAngkaRibuanDouble(nominalAdminstrasi.toDouble())
            val parsedTenor = FormatterAngka.penghilangNilaiKoma(jumlahTenor)
            val persenanJasa = res.data?.get(0)?.interest.toString()
            val angsBln =res.data?.get(0)?.instal.toString()

            val danaDiterima = res.data?.get(0)?.amount.toString()


            when (tipePotongan) {
                "AUTD" -> {
                    binding.tvTipePinjaman.text = "Auto Debet"
                }

                "BAT" -> {
                    binding.tvTipePinjaman.text = "BAT"
                }

                "BNS" -> {
                    binding.tvTipePinjaman.text = "Bonus"
                }

                "COS" -> {
                    binding.tvTipePinjaman.text = "COS"
                }

                "CUTI" -> {
                    binding.tvTipePinjaman.text = "Cuti"
                }

                "DIS" -> {
                    binding.tvTipePinjaman.text = "DIS"
                }

                "FGS" -> {
                    binding.tvTipePinjaman.text = "Fungsional"
                }

                "GAJI" -> {
                    binding.tvTipePinjaman.text = "Gaji"
                }

                "GJ13" -> {
                    binding.tvTipePinjaman.text = "Gaji 13"
                }

                "INST" -> {
                    binding.tvTipePinjaman.text = "INSENTIF"
                }

                "MBL" -> {
                    binding.tvTipePinjaman.text = "Mobilitas"
                }

                "PMS" -> {
                    binding.tvTipePinjaman.text = "PMS"
                }

                "PPAN" -> {
                    binding.tvTipePinjaman.text = "Pinjaman Jangka Panjang"
                }

                "SHF" -> {
                    binding.tvTipePinjaman.text = "SHIFT"
                }

                "THR" -> {
                    binding.tvTipePinjaman.text = "THR"
                }

                "TNBL" -> {
                    binding.tvTipePinjaman.text = "Tunai Bulanan"
                }

                "TNTH" -> {
                    binding.tvTipePinjaman.text = "Tunai Tahunan"
                }

                "TRN" -> {
                    binding.tvTipePinjaman.text = "Transport"
                }
            }

            binding.tvTgl.text = parsedTgl
            binding.tvNoRef.text = res.data?.get(0)?.docNum
            binding.tvNominaJumlahPinjaman.text = parsedNominalPinjaman
            binding.tvNominalJasa.text = parsedNominalJasa
            binding.tvNominalAdministrasi.text = persenanAdmin
            binding.tvNominalJasa.text = persenanJasa
            binding.tvNominalDanaDiterima.text = FormatterAngka.formatterNilaiUangIndonesia(danaDiterima.toDouble())
            binding.tvTenor.text = parsedTenor
            binding.tvNominalAngsBln.text = FormatterAngka.formatterNilaiUangIndonesia(angsBln.toDouble())
        }
    }


    private fun fetchData() {
        lifecycleScope.launch {
            detailPinjamanInfoViewModel.getDetailPinjamanInfo(extraDocnum)
        }

    }


    companion object {
        private val TAG = DetailPinjamanInfoActivity::class.java.simpleName
        const val EXTRA_DOCNUM = "extra_docnum"
    }
}