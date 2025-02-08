package com.alkindi.kopkar.ui.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.alkindi.kopkar.data.local.model.ProcessedCalculation
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.data.remote.response.JenisPinjamanDataItem
import com.alkindi.kopkar.databinding.ActivityPinjamanBinding
import com.alkindi.kopkar.ui.viewmodel.PinjamanViewModel
import com.alkindi.kopkar.utils.AndroidUIHelper
import com.alkindi.kopkar.utils.FormatterAngka
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class PinjamanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityPinjamanBinding
    private lateinit var userID: String
    private var tipePinjaman: String = ""
    private var selectedTenor: String = ""
    private var selectedTipePotongan: String = ""
    private lateinit var extraData: ProcessedCalculation
    private val pinjamanViewModel: PinjamanViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinjamanBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSession()
        btnAturNominalLogic()
        chipButtonLogic()
        checkLoading()
        observeTipePotonganData()

        binding.btnBack.setOnClickListener {
            finish()
        }


        binding.btnLanjutkan?.setOnClickListener {
            hitungPersenanPinjaman()
            checkHitungStatus()
        }

    }

    private fun checkHitungStatus() {
        pinjamanViewModel.hitungAdmResponse.observe(this) { res ->
            if (res.code == 200) {
                val amp = res.data?.smt?.amp.toString()
                val intadm = res.data?.smt?.intadm.toString()
                val totam = res.data?.smt?.totam.toString()
                val aminst = res.data?.smt?.aminst.toString()
                val amJasa = res.data?.smt?.amjasa.toString()
                val amAdm = res.data?.smt?.amadm.toString()
                val plafon = res.data?.smt?.plafon.toString()
                val jmlNominalPinjaman = binding.tvJumlahNominal.text.toString()

                extraData =
                    ProcessedCalculation(
                        amp = amp,
                        intadm = intadm,
                        totam = totam,
                        aminst = aminst,
                        amjasa = amJasa,
                        amAdm = amAdm,
                        plafon = plafon,
                        nominalPinjaman = jmlNominalPinjaman,
                        tenor = selectedTenor,
                        tipePotongan = selectedTipePotongan
                    )
                val toDetailPinjaman =
                    Intent(this@PinjamanActivity, NominalPinjamanActivity::class.java).apply {
                        putExtra(NominalPinjamanActivity.EXTRA_DATA, extraData)
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                startActivity(toDetailPinjaman)
            } else {
                AndroidUIHelper.showAlertDialog(
                    this,
                    "Error",
                    "Tidak dapat melanjutkan: ${res.message.toString()}"
                )
            }
        }
    }

    private fun hitungPersenanPinjaman() {
        val tipePinjaman = binding.spinnerTipePotongan!!.selectedItem.toString()
        val jmlNominal = binding.tvJumlahNominal.text.toString()
        val tenor = binding.spinnerTenor!!.selectedItem.toString()
        val tipePotongan = binding.spinnerTipePotongan!!.selectedItem.toString()
        selectedTenor = tenor
        selectedTipePotongan = tipePotongan

        lifecycleScope.launch {
            pinjamanViewModel.hitungAdmPinjaman(tipePinjaman, jmlNominal, tenor)
        }
    }

    private fun getSession() {
        pinjamanViewModel.getSession().observe(this) {
            userID = it.username
        }
    }


    private fun observeTipePotonganData() {
        pinjamanViewModel.listPotonganResponse.observe(this) { resp ->
            resp?.let {
                if (it.code == 200 && it.data != null) {
                    addSpinnerData(it.data)
                } else {
                    AndroidUIHelper.showWarningToastShort(this, "Failed to load data")
                }
            }
        }
    }

    private fun addSpinnerData(data: List<JenisPinjamanDataItem?>) {
        val listPitCode = data.mapNotNull { it?.pitcode }
        val spinnerAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, listPitCode)
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipePotongan?.adapter = spinnerAdapter
    }

    private fun checkLoading() {
        pinjamanViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar?.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            binding.progressBar?.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun btnAturNominalLogic() {

        binding.btnTambahPinjaman.setOnClickListener {
            tambahNilaiPinjaman()
        }

        binding.btnKrgPinjaman.setOnClickListener {
            kurangiNilaiPinjaman()
        }

        if (binding.tvJumlahNominal.text == "0") {
            binding.btnKrgPinjaman.visibility = View.GONE
        }
    }

    private fun chipButtonLogic() {
        binding.chip1jt.setOnClickListener {
            updateNominalValue(binding.chip1jt)
        }
        binding.chip5jt.setOnClickListener {
            updateNominalValue(binding.chip5jt)
        }
        binding.chip10jt.setOnClickListener {
            updateNominalValue(binding.chip10jt)
        }
    }

    private fun updateNominalValue(chip: Chip) {
        val nilaiChip = chip.text.toString().replace("Rp", "").replace(".", "")
        val formattedValue = FormatterAngka.formatterAngkaRibuan(nilaiChip.toInt())
        binding.tvJumlahNominal.text = formattedValue
    }

    private fun tambahNilaiPinjaman() {
        val tvNumberValue = binding.tvJumlahNominal.text
        val currentNumber = tvNumberValue.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) + 10000
        binding.tvJumlahNominal.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        binding.btnKrgPinjaman.visibility = View.VISIBLE
    }

    private fun kurangiNilaiPinjaman() {
        val tvNumberValue = binding.tvJumlahNominal.text
        val currentNumber = tvNumberValue.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) - 10000
        binding.tvJumlahNominal.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        if (binding.tvJumlahNominal.text == "0") {
            binding.btnKrgPinjaman.visibility = View.GONE
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
        val selectedItem: String = parent?.getItemAtPosition(position).toString()
        tipePinjaman = selectedItem
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    companion object {
        private val TAG = PinjamanActivity::class.java.simpleName
    }

}