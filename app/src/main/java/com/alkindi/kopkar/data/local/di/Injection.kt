package com.alkindi.kopkar.data.local.di

import android.content.Context
import com.alkindi.kopkar.data.local.db.AkorDB
import com.alkindi.kopkar.data.local.pref.UserPreference
import com.alkindi.kopkar.data.local.pref.datastore
import com.alkindi.kopkar.data.remote.retrofit.ApiConfig
import com.alkindi.kopkar.data.repository.UserRepository
import com.alkindi.kopkar.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.datastore)
        val apiService = ApiConfig.getApiService()
        val db = AkorDB.getDB(context)
        val dao = db.userPersonalDAO()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, pref, dao)
    }
}