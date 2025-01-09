package com.alkindi.kopkar.data.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alkindi.kopkar.data.local.di.Injection
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.ui.viewmodel.DetailHistoryPinjamanViewModel
import com.alkindi.kopkar.ui.viewmodel.DetailPinjamanInfoViewModel
import com.alkindi.kopkar.ui.viewmodel.DetailSimpananFragmentViewModel
import com.alkindi.kopkar.ui.viewmodel.EditProfileViewModel
import com.alkindi.kopkar.ui.viewmodel.HistoryPinjamanViewModel
import com.alkindi.kopkar.ui.viewmodel.HomeViewModel
import com.alkindi.kopkar.ui.viewmodel.LoginViewModel
import com.alkindi.kopkar.ui.viewmodel.MainViewModel
import com.alkindi.kopkar.ui.viewmodel.NominalPinjamanViewModel
import com.alkindi.kopkar.ui.viewmodel.PersonalDataViewModel
import com.alkindi.kopkar.ui.viewmodel.PinjamanViewModel
import com.alkindi.kopkar.ui.viewmodel.ProfileViewModel
import com.alkindi.kopkar.ui.viewmodel.RiwayatTransaksiFragmentViewModel
import com.alkindi.kopkar.ui.viewmodel.TarikNominalSimpananViewModel
import com.alkindi.kopkar.ui.viewmodel.TarikSimpananProcessedViewModel
import com.alkindi.kopkar.ui.viewmodel.TarikSimpananViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(PersonalDataViewModel::class.java) -> {
                PersonalDataViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(TarikSimpananViewModel::class.java) -> {
                TarikSimpananViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(RiwayatTransaksiFragmentViewModel::class.java) -> {
                RiwayatTransaksiFragmentViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(TarikNominalSimpananViewModel::class.java)->{
                TarikNominalSimpananViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(PinjamanViewModel::class.java)->{
                PinjamanViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(HistoryPinjamanViewModel::class.java)->{
                HistoryPinjamanViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(TarikSimpananProcessedViewModel::class.java)->{
                TarikSimpananProcessedViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailSimpananFragmentViewModel::class.java)->{
                DetailSimpananFragmentViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailHistoryPinjamanViewModel::class.java)->{
                DetailHistoryPinjamanViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java)->{
                EditProfileViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(NominalPinjamanViewModel::class.java)->{
                NominalPinjamanViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailPinjamanInfoViewModel::class.java)->{
                DetailPinjamanInfoViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown Viewmodel Class: ${modelClass.name}")
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            val userRepository = Injection.provideRepository(context)
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(userRepository)
            }.also { instance = it }
        }
    }
}