package com.alkindi.kopkar.ui.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkindi.kopkar.data.local.model.UserModel
import com.alkindi.kopkar.data.model.ViewModelFactory
import com.alkindi.kopkar.databinding.ActivityLoginBinding
import com.alkindi.kopkar.ui.viewmodel.LoginViewModel
import com.alkindi.kopkar.utils.AndroidUIHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMasuk.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (isInternetActive()) {
                    loginViewModel.signInUser(username, password)
                    showLoading(true)
                } else {
                    AndroidUIHelper.showAlertDialog(
                        this,
                        "Error",
                        "Aplikasi hanya dapat dijalankan dengan koneksi internet"
                    )
                }
            } else {
                AndroidUIHelper.showWarningToastShort(this, "Silahkan Lengkapi Username & Password")
            }
        }

        loginViewModel.checkSession()

        loginViewModel.loginResponse.observe(this) { response ->
            if (response.e == "Login Failed") {
                AndroidUIHelper.showAlertDialog(
                    this,
                    "Login Gagal",
                    "Tidak dapat melanjutkan proses login!"
                )
                showLoading(false)
            } else if (response.pid == "1") {
                val userData = UserModel(
                    binding.edtUsername.text.toString(),
                    binding.edtPassword.text.toString(),
                    true
                )
                loginViewModel.saveSession(userData)
                navigateToHomeScreen()
                AndroidUIHelper.showWarningToastShort(this, "Anda telah berhasil login")
            } else {
                binding.tvWarningLogin.visibility = View.VISIBLE
                Log.d(
                    TAG,
                    "INSERTED USERNAME: ${binding.edtUsername.text} INSERTED PASSWORD: ${binding.edtPassword.text}"
                )
            }
        }

        loginViewModel.isLoading.observe(this) { response ->
            showLoading(response)
        }
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) =
        if (isLoading) binding.progressBar.visibility =
            View.VISIBLE else binding.progressBar.visibility = View.GONE


    private fun isInternetActive(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(networkInfo)
        return networkCapabilities != null && (networkCapabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        ) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }
}