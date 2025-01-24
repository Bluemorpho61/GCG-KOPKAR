package com.alkindi.kopkar.data.remote.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiConfig {
    const val BASE_URL_KOPKAR = "https://kopkarjict.com/"
    const val API_DEV_CODE_KOPKAR = "6t0KxJnF8gYJmkDj52TkQw"
    const val WORKSPACE_CODE_KOPKAR = "JICTAKORDEV"
    fun getApiService(): ApiService {

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val trustAllCertf = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertf, SecureRandom())
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCertf[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_KOPKAR)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

