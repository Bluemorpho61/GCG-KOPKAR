package com.alkindi.kopkar.ui.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.alkindi.kopkar.data.local.model.InputtedBiayaPot
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.data.remote.response.TenorListItem
import com.alkindi.kopkar.data.remote.response.TipePotonganItem
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
        observeListTenor()

        binding.btnBack.setOnClickListener {
            finish()
        }


        binding.btnLanjutkan?.setOnClickListener {
            if (tipePinjaman == "RUMAH") {
                if (!binding.edtNominalPotongan?.text.isNullOrEmpty() && !binding.edtPotonganPribadi?.text.isNullOrEmpty() && !binding.edtNoAtasan?.text.isNullOrEmpty() && !binding.edtTenor?.text.isNullOrEmpty()) {
                    toInputNominalPinjaman()
                } else {
                    AndroidUIHelper.showWarningToastShort(
                        this, "Silahkan isi semua field yang telah disediakan"
                    )
                    return@setOnClickListener
                }
            } else if (tipePinjaman == "MOBIL") {
                if (!binding.edtNominalPotongan?.text.isNullOrEmpty() && !binding.edtPotonganPribadi?.text.isNullOrEmpty() && !binding.edtNoAtasan?.text.isNullOrEmpty() && !binding.edtTenor?.text.isNullOrEmpty()) {
                    toInputNominalPinjaman()
                } else {
                    AndroidUIHelper.showWarningToastShort(
                        this, "Silahkan isi semua field yang telah disediakan"
                    )
                    return@setOnClickListener
                }
            } else {
                if (!binding.edtNominalPotongan?.text.isNullOrEmpty() && !binding.edtPotonganPribadi?.text.isNullOrEmpty() && !binding.edtNoAtasan?.text.isNullOrEmpty()) {
                    toInputNominalPinjaman()
                } else {
                    AndroidUIHelper.showWarningToastShort(
                        this, "Silahkan isi semua field yang telah disediakan"
                    )
                }
            }


        }

        binding.spinnerTipePinjaman?.onItemSelectedListener = this


    }

    private fun observeListTenor() {
        pinjamanViewModel.listTenor.observe(this) { res ->
            res.let {
                if (res.code == 200 && res.data != null) {
                    addTenorList(it.data)
                }
            }
        }
    }

    private fun addTenorList(data: List<TenorListItem?>?) {
        val listTenorValue =
            data?.mapNotNull { it?.tenor?.let { Pair(it.toInt().toString(), it) } } ?: emptyList()
        val spinnerAdapter = object : ArrayAdapter<String>(this,
            R.layout.simple_spinner_item,
            listTenorValue.map { it.first }) {
            override fun getDropDownView(
                position: Int, convertView: View?, parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                (view as TextView).text = listTenorValue[position].first
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerTenor?.adapter = spinnerAdapter
        binding.spinnerTenor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                listTenorValue[position]
                selectedTenor = listTenorValue[position].second.toString()
                Log.d(TAG, "Selected Value: $selectedTenor")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun toInputNominalPinjaman() {
        val nomTipePotongan = binding.edtNominalPotongan?.text.toString().replace(",", "")  //sal
        val nomPotPribadi = binding.edtPotonganPribadi?.text.toString().replace(",", "") //pot
        val noAtasan = binding.edtNoAtasan?.text.toString()
        val mbrid = userID
        val tipePinjaman = tipePinjaman
        val tipePotongan = binding.spinnerTipePotongan?.selectedItem.toString()
        val tenorPinjaman = selectedTenor
        val tglPencairan = binding.edtTglPencairan?.text.toString()
        val edtTenorPinjaman = binding.edtTenor?.text.toString()
        val tglBonus = binding.edtTglBonus?.text.toString()

        if (tipePinjaman == "RUMAH") {
            val extraData = InputtedBiayaPot(
                tipePinjaman,
                tipePotongan,
                nomTipePotongan,
                nomPotPribadi,
                noAtasan,
                edtTenorPinjaman,
                tglPencairan,
                tglBonus,
                null
            )
            val toInputNominal = Intent(this@PinjamanActivity, NominalPinjamanActivity::class.java)
            toInputNominal.putExtra(NominalPinjamanActivity.EXTRA_DATA, extraData)
            startActivity(toInputNominal)
        } else if (tipePinjaman == "MOBIL") {
            val extraData = InputtedBiayaPot(
                tipePinjaman,
                tipePotongan,
                nomTipePotongan,
                nomPotPribadi,
                noAtasan,
                edtTenorPinjaman,
                tglPencairan,
                tglBonus,
                null
            )
            val toInputNominal = Intent(this@PinjamanActivity, NominalPinjamanActivity::class.java)
            toInputNominal.putExtra(NominalPinjamanActivity.EXTRA_DATA, extraData)
            startActivity(toInputNominal)
        } else {
            val extraData = InputtedBiayaPot(
                tipePinjaman,
                tipePotongan,
                nomTipePotongan,
                nomPotPribadi,
                noAtasan,
                tenorPinjaman,
                tglPencairan,
                tglBonus,
                null
            )
            val toInputNominal = Intent(this@PinjamanActivity, NominalPinjamanActivity::class.java)
            toInputNominal.putExtra(NominalPinjamanActivity.EXTRA_DATA, extraData)
            startActivity(toInputNominal)
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

    private fun addSpinnerData(data: List<TipePotonganItem?>) {
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

        binding.btnTambahPinjaman?.setOnClickListener {
            tambahNilaiPinjaman()
        }

        binding.btnKrgPinjaman?.setOnClickListener {
            kurangiNilaiPinjaman()
        }

        if (binding.tvJumlahNominal?.text == "0") {
            binding.btnKrgPinjaman?.visibility = View.GONE
        }
    }

    private fun chipButtonLogic() {
        binding.chip1jt?.setOnClickListener {
            updateNominalValue(binding.chip1jt!!)
        }
        binding.chip5jt?.setOnClickListener {
            updateNominalValue(binding.chip5jt!!)
        }
        binding.chip10jt?.setOnClickListener {
            updateNominalValue(binding.chip10jt!!)
        }
    }

    private fun updateNominalValue(chip: Chip) {
        val nilaiChip = chip.text.toString().replace("Rp", "").replace(".", "")
        val formattedValue = FormatterAngka.formatterAngkaRibuan(nilaiChip.toInt())
        binding.tvJumlahNominal?.text = formattedValue
    }

    private fun tambahNilaiPinjaman() {
        val tvNumberValue = binding.tvJumlahNominal?.text
        val currentNumber = tvNumberValue.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) + 10000
        binding.tvJumlahNominal?.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        binding.btnKrgPinjaman?.visibility = View.VISIBLE
    }

    private fun kurangiNilaiPinjaman() {
        val tvNumberValue = binding.tvJumlahNominal?.text
        val currentNumber = tvNumberValue.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) - 10000
        binding.tvJumlahNominal?.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        if (binding.tvJumlahNominal?.text == "0") {
            binding.btnKrgPinjaman?.visibility = View.GONE
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
        val selectedItem: String = parent?.getItemAtPosition(position).toString()

        when (selectedItem) {
            "Jangka Panjang" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("JAPAN")
                    pinjamanViewModel.getTenorList("JAPAN")
                }
                tipePinjaman = "JAPAN"
                binding.tvSimpananPagu?.visibility = View.VISIBLE
                binding.edtSimpananPagu?.visibility = View.VISIBLE
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvProvisi?.visibility = View.VISIBLE
                binding.edtProvisi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvTglPencairan?.visibility = View.GONE
                binding.edtTglPencairan?.visibility = View.GONE
                binding.tvTglBonus?.visibility = View.GONE
                binding.edtTglBonus?.visibility = View.GONE


                binding.spinnerTenor?.visibility = View.VISIBLE
                binding.edtTenor?.visibility = View.GONE
            }

            "Rumah" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("RUMAH")
                    pinjamanViewModel.getTenorList("RUMAH")
                }
                tipePinjaman = "RUMAH"
                binding.tvSimpananPagu?.visibility = View.VISIBLE
                binding.edtSimpananPagu?.visibility = View.VISIBLE
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvProvisi?.visibility = View.VISIBLE
                binding.edtProvisi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvTglPencairan?.visibility = View.GONE
                binding.edtTglPencairan?.visibility = View.GONE
                binding.tvTglBonus?.visibility = View.GONE
                binding.edtTglBonus?.visibility = View.GONE

                binding.spinnerTenor?.visibility = View.GONE
                binding.edtTenor?.visibility = View.VISIBLE
            }

            "Kredit Barang" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("BRANG")
                    pinjamanViewModel.getTenorList("BRANG")
                }
                tipePinjaman = "BRANG"
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvSimpananPagu?.visibility = View.GONE
                binding.edtSimpananPagu?.visibility = View.GONE
                binding.tvAdministrasi?.visibility = View.GONE
                binding.edtAdministrasi?.visibility = View.GONE
                binding.tvAsuransi?.visibility = View.GONE
                binding.edtAsuransi?.visibility = View.GONE
                binding.tvProvisi?.visibility = View.GONE
                binding.edtProvisi?.visibility = View.GONE
                binding.tvTglPencairan?.visibility = View.GONE
                binding.edtTglPencairan?.visibility = View.GONE
                binding.tvTglBonus?.visibility = View.GONE
                binding.edtTglBonus?.visibility = View.GONE

                binding.spinnerTenor?.visibility = View.VISIBLE
                binding.edtTenor?.visibility = View.GONE
            }

            "Mobil" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("MOBIL")
                    pinjamanViewModel.getTenorList("MOBIL")
                }
                tipePinjaman = "MOBIL"
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvProvisi?.visibility = View.VISIBLE
                binding.edtProvisi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvSimpananPagu?.visibility = View.GONE
                binding.edtSimpananPagu?.visibility = View.GONE
                binding.tvTglPencairan?.visibility = View.GONE
                binding.edtTglPencairan?.visibility = View.GONE
                binding.tvTglBonus?.visibility = View.GONE
                binding.edtTglBonus?.visibility = View.GONE

                binding.spinnerTenor?.visibility = View.GONE
                binding.edtTenor?.visibility = View.VISIBLE
            }

            "Motor" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("MOTOR")
                    pinjamanViewModel.getTenorList("MOTOR")
                }
                tipePinjaman = "MOTOR"
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvSimpananPagu?.visibility = View.GONE
                binding.edtSimpananPagu?.visibility = View.GONE
                binding.tvAdministrasi?.visibility = View.GONE
                binding.edtAdministrasi?.visibility = View.GONE
                binding.tvProvisi?.visibility = View.GONE
                binding.edtProvisi?.visibility = View.GONE
                binding.tvTglPencairan?.visibility = View.GONE
                binding.edtTglPencairan?.visibility = View.GONE
                binding.tvTglBonus?.visibility = View.GONE
                binding.edtTglBonus?.visibility = View.GONE

                binding.spinnerTenor?.visibility = View.VISIBLE
                binding.edtTenor?.visibility = View.GONE
            }

            "Jangka Pendek" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("JAPEN")
                    pinjamanViewModel.getTipePotonganList("JAPEN")
                }
                tipePinjaman = "JAPEN"
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvTglPencairan?.visibility = View.VISIBLE
                binding.edtTglPencairan?.visibility = View.VISIBLE
                binding.tvTglBonus?.visibility = View.VISIBLE
                binding.edtTglBonus?.visibility = View.VISIBLE

                binding.spinnerTenor?.visibility = View.VISIBLE
                binding.edtTenor?.visibility = View.GONE
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    companion object {
        private val TAG = PinjamanActivity::class.java.simpleName
    }

}