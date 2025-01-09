package com.alkindi.kopkar.ui.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.databinding.ActivityMainBinding
import com.alkindi.kopkar.ui.viewmodel.MainViewModel
import com.alkindi.kopkar.utils.AndroidUIHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    //    private val splashScreenDuration: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkSession()
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        checkResponse()
    }

    private fun checkResponse() {
        mainViewModel.loginResponse.observe(this) {
            if (it.e == "Login Failed") {
                AndroidUIHelper.showAlertDialog(
                    this,
                    "Login Gagal",
                    "Tidak dapat masuk kedalam aplikasi!"
                )
            } else if (it.pid == "1") {
                routeToHome()
            } else {
                AndroidUIHelper
            }
        }
    }

    private fun routeToHome() {
        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        finish()
    }


    private fun showLoading(it: Boolean) =
        if (it) binding.progressBar2.visibility =
            View.VISIBLE else binding.progressBar2.visibility = View.GONE

    private fun checkSession() {
        if (isInternetActive()) {
            mainViewModel.getSession().observe(this) {
                if (it.isLogin) {
                    mainViewModel.signInUser(it.username, it.password)
                } else {
                    showSplashScreen()
                }
            }
        } else {
            AndroidUIHelper.showAlertDialog(
                this,
                "Tidak Ada Koneksi Internet",
                "Aplikasi hanya bisa dijalankan dengan koneksi internet. Klik OK untuk menutup aplikasi"
            )
            finishAffinity()
            exitProcess(0)
        }
    }

    private fun showSplashScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            routeToLogin()
            finish()
        }
    }

    private fun routeToLogin() {
        val toLogin = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(toLogin)
    }

    private fun isInternetActive(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(networkInfo)
        return networkCapabilities != null && (networkCapabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        ) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}