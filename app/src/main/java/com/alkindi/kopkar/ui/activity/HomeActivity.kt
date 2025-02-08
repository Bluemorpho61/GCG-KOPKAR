package com.alkindi.kopkar.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkindi.kopkar.R
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.data.remote.customizednetworkingclass.ImageLoaderCustom
import com.alkindi.kopkar.data.remote.response.RiwayatTransaksiItem
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.databinding.ActivityHomeBinding
import com.alkindi.kopkar.ui.adapter.RiwayatTransaksiHomeAdapter
import com.alkindi.kopkar.ui.viewmodel.HomeViewModel
import com.alkindi.kopkar.utils.AndroidUIHelper
import com.alkindi.kopkar.utils.FormatterAngka
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private val list = ArrayList<RiwayatTransaksiItem>()
    private lateinit var userID: String
    private var pressedBack = false
    private lateinit var binding: ActivityHomeBinding
    private var nominalPinjaman: String? = null

    private var isNominalVisible = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSession()
        getTotalPinjaman()
        getRiwayatData()
        checkLoading()
        showTotalPinjaman()
        getImageData()
        showUserImage()
        homeViewModel.checkSavedLoginData()


        binding.btnPersonalData.setOnClickListener {
            val toPersonalData = Intent(this@HomeActivity, PersonalDataActivity::class.java)
            startActivity(toPersonalData)
        }
        binding.btnPinjaman.setOnClickListener {
            val toPinjamanActivity = Intent(this@HomeActivity, PinjamanActivity::class.java)
            startActivity(toPinjamanActivity)
        }
        binding.btnHistory.setOnClickListener {
            val toHistoryActivity = Intent(this@HomeActivity, HistoryPinjamanActivity::class.java)
            startActivity(toHistoryActivity)
        }
        binding.btnTarikSimpanan.setOnClickListener {
            val toTarikSimpanan = Intent(this@HomeActivity, TarikSimpananActivity::class.java)
            startActivity(toTarikSimpanan)
        }
        binding.btnTambahPinjaman?.setOnClickListener {
            val toTambahPinjaman = Intent(this@HomeActivity, PinjamanActivity::class.java)
            startActivity(toTambahPinjaman)
        }
        binding.imProfile.setOnClickListener {
            val toProfile = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(toProfile)
        }
        binding.btnNominalVisibility?.setOnClickListener {
            toggleNominalVisibility()
        }


        getRvData()
        this.onBackPressedDispatcher.addCallback(
            this,
            doubleBackPressedOnce
        )
    }

    private fun toggleNominalVisibility() {
        if (isNominalVisible) {
            binding.tvNominalPinjaman?.text = "............."
            binding.btnNominalVisibility?.setImageResource(R.drawable.baseline_visibility_black)
        } else {
            binding.tvNominalPinjaman?.text = nominalPinjaman
            binding.btnNominalVisibility?.setImageResource(R.drawable.baseline_visibility_off_black)
        }
        isNominalVisible = !isNominalVisible
    }

    private fun showUserImage() {
        homeViewModel.userImageResponse.observe(this) { res ->
            ImageLoaderCustom(binding.imProfile).execute("${ApiConfig.BASE_URL_KOPKAR}${res.data}")
        }
    }

    private fun getImageData() {
        homeViewModel.getSession().observe(this) {
            lifecycleScope.launch {
                homeViewModel.getUserImage(it.username, ApiConfig.WORKSPACE_CODE_KOPKAR)
            }
        }

    }

    private fun getRiwayatData() {
        homeViewModel.getSession().observe(this) { res ->
            lifecycleScope.launch {
                homeViewModel.getRiwayatTransaksi(res.username)
            }
        }
    }


    private fun checkLoading() {
        homeViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar?.visibility = View.VISIBLE
            binding.rvRiwayatTransaksiHome.visibility = View.GONE
        } else {
            binding.progressBar?.visibility = View.GONE
            binding.rvRiwayatTransaksiHome.visibility = View.VISIBLE
        }
    }

    private fun showTotalPinjaman() {
        homeViewModel.totalPinjamanResponse.observe(this) { res ->
            val nominalTotalPinjaman = res.data?.sum ?: 0
            val formattedTotalPinjaman = FormatterAngka.formatterAngkaRibuan(nominalTotalPinjaman)
            nominalPinjaman = formattedTotalPinjaman

            binding.tvNominalPinjaman?.text = formattedTotalPinjaman
        }
    }

    private fun getSession() {
        homeViewModel.getSession().observe(this) {
            userID = it.username
            getTotalPinjaman()
        }
    }

    private fun getTotalPinjaman() {
        lifecycleScope.launch {
            if (::userID.isInitialized) {
                homeViewModel.getTotalPinjaman(userID)
            } else {
                Log.e(TAG, "userID is not initialized")
            }
        }
    }


    private fun getRvData() {
        binding.rvRiwayatTransaksiHome.layoutManager = LinearLayoutManager(this)
        val adapter = RiwayatTransaksiHomeAdapter()
        homeViewModel.riwayatTransaksiResponse.observe(this) { res ->
            res.data?.filterNotNull()?.let { nonNullData ->
                list.addAll(nonNullData)
                adapter.submitList(nonNullData)
                binding.rvRiwayatTransaksiHome.adapter = adapter
            }
        }
    }


    private val doubleBackPressedOnce = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (pressedBack) {
                finishAffinity()
            } else {
                pressedBack = true
                AndroidUIHelper.showWarningToastShort(
                    this@HomeActivity,
                    "Tekan BACK kembali untuk keluar dari aplikasi"
                )
                binding.root.postDelayed({ pressedBack = false }, 2000)
            }
        }
    }

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}