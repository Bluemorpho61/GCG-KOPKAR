package com.alkindi.kopkar.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.alkindi.kopkar.R
import com.alkindi.kopkar.data.local.model.SimpTypeWNominal
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.databinding.ActivityTarikSimpananBinding
import com.alkindi.kopkar.ui.fragment.DetailSimpananFragment
import com.alkindi.kopkar.ui.fragment.RiwayatTransaksiFragment
import com.alkindi.kopkar.ui.viewmodel.TarikSimpananViewModel
import com.alkindi.kopkar.utils.AndroidUIHelper
import com.alkindi.kopkar.utils.FormatterAngka
import kotlinx.coroutines.launch

class TarikSimpananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTarikSimpananBinding
    private val tarikSimpananViewModel: TarikSimpananViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var userID: String
    private lateinit var nominalValue: String
    private var isNominalVisible = true
    private var setShownFragment = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarikSimpananBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkLoading()
        fetchNominalSimpanan()
        getUserID()
        showUserNominal()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentDetailSimpanan, RiwayatTransaksiFragment()).commit()

        binding.btnTarikSimpanan.setOnClickListener {
            val extraData = SimpTypeWNominal(
                binding.tvNominalSimpananSukarela.text.toString()
            )

            val toTarikNominal =
                Intent(this@TarikSimpananActivity, TarikNominalSimpananActivity::class.java)

            toTarikNominal.putExtra(TarikNominalSimpananActivity.EXTRA_SIMP_TYPE, extraData)
            startActivity(toTarikNominal)
        }

        binding.btnDetailSimpanan.setOnClickListener {
            if (setShownFragment) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentDetailSimpanan, DetailSimpananFragment(), "frag_detail")
                    .commit()
                binding.btnDetailSimpanan.text = "Detail Simpanan"
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentDetailSimpanan, RiwayatTransaksiFragment(), "frag_detail")
                    .commit()
                binding.btnDetailSimpanan.text = "Riwayat Transaksi"
            }
            setShownFragment = !setShownFragment
        }

        binding.btnBack.setOnClickListener {
            finish()
        }


        binding.btnNominalVisibility.setOnClickListener {
            toggleNominalVisibility()
        }

    }

    private fun fetchNominalSimpanan() {
        tarikSimpananViewModel.dataSimpananUser.observe(this) { res ->
            if (res.code == 500) {
                AndroidUIHelper.showAlertDialog(
                    this,
                    "Error",
                    "Gagal mengambil data dikarenakan tidak dapat terhubung dengan API. Silahkan hubungi administrator"
                )
            } else {
                val nominalSimpananTersisa = res.data?.get(0)?.sskr.toString()
                val formattedNominal =
                    FormatterAngka.formatterAngkaRibuanDouble(nominalSimpananTersisa.toDouble())
                nominalValue = formattedNominal
                binding.tvNominalSimpananSukarela.text = nominalValue
            }

        }

    }

    private fun passDataToFragment(selectedValue: String) {
        val fragment = DetailSimpananFragment()

        val bundle = Bundle()
        bundle.putString("selected_val", selectedValue)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentDetailSimpanan, fragment)
            .commit()
    }

    private fun showUserNominal() {
        tarikSimpananViewModel.dataSimpananUser.observe(this) {
            if (it.code == 500) {
                AndroidUIHelper.showAlertDialog(
                    this,
                    "Error",
                    "Gagal mengambil data dikarenakan tidak dapat terhubung dengan API. Silahkan hubungi administrator"
                )
            } else {
                fetchNominalSimpanan()
            }
        }
    }

    private fun checkLoading() {
        tarikSimpananViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    private fun getUserID() {
        tarikSimpananViewModel.getSession().observe(this) {
            userID = it.username
            lifecycleScope.launch {
                tarikSimpananViewModel.getDataSimpananUser(userID)
            }
        }
    }


    private fun toggleNominalVisibility() {
        if (isNominalVisible) {
            binding.tvNominalSimpananSukarela.text = "............."
            binding.btnNominalVisibility.setImageResource(R.drawable.baseline_visibility_24)
        } else {
            binding.tvNominalSimpananSukarela.text = nominalValue
            binding.btnNominalVisibility.setImageResource(R.drawable.baseline_visibility_off_24)
        }
        isNominalVisible = !isNominalVisible
    }


    companion object {
        private val TAG = TarikSimpananActivity::class.java.simpleName
    }

}