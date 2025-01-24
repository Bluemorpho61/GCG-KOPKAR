package com.alkindi.kopkar.data.remote.retrofit

import com.alkindi.kopkar.data.remote.response.AjukanPinjamanResponse
import com.alkindi.kopkar.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.response.DetailSimpananResponse
import com.alkindi.kopkar.data.remote.response.EditPersonalDataResponse
import com.alkindi.kopkar.data.remote.response.HistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.response.HitungAdmPinjamanResponse
import com.alkindi.kopkar.data.remote.response.JenisPinjamanResponse
import com.alkindi.kopkar.data.remote.response.LoginResponse
import com.alkindi.kopkar.data.remote.response.NominalSimpananResponse
import com.alkindi.kopkar.data.remote.response.PersonalDataResponse
import com.alkindi.kopkar.data.remote.response.RiwayatTarikSimpananResponse
import com.alkindi.kopkar.data.remote.response.RiwayatTransaksiResponse
import com.alkindi.kopkar.data.remote.response.TarikNominalSimpananResponse
import com.alkindi.kopkar.data.remote.response.TarikSimpananProcessedResponse
import com.alkindi.kopkar.data.remote.response.TotalPinjamanResponse
import com.alkindi.kopkar.data.remote.response.UpdateProfileResponse
import com.alkindi.kopkar.data.remote.response.UserProfileImageResponse
import com.alkindi.kopkar.data.remote.response.UserProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @FormUrlEncoded
    @POST("preodm?fnc=auth")
    suspend fun login(
        @Field("csn") workSpace: String,
        @Field("usn") username: String,
        @Field("pwd") password: String,
        @Field("unifie") unifie: String,
        @Field("urifie") urifie: String
    ): LoginResponse

    @GET
    suspend fun getProfileExt(  // New getProfile API endpoint
        @Url fullUrl: String
    ): UserProfileResponse

    @GET
    suspend fun getPersonal(
        @Url fullUrl: String
    ): PersonalDataResponse

    @GET
    suspend fun getNominalSimpananUser(
        @Url fullUrl: String
    ): NominalSimpananResponse

    @GET
    suspend fun getJenisPinjamanList(
        @Url fullUrl: String
    ): JenisPinjamanResponse

    @GET
    suspend fun getHistoryPinjaman(
        @Url fullUrl: String
    ): HistoryPinjamanResponse

    @GET
    suspend fun getDetailSimpanan(
        @Url fullUrl: String
    ): DetailSimpananResponse

    @GET
    suspend fun getDetailHistoryPinjaman(
        @Url fullUrl: String
    ): DetailHistoryPinjamanResponse

    @GET
    suspend fun getTotalPinjamanAmount(
        @Url fullUrl: String
    ): TotalPinjamanResponse

    @GET
    suspend fun getRiwayatTransaksi(
        @Url fullUrl: String
    ): RiwayatTransaksiResponse

    @GET
    suspend fun getImageGambar(
        @Url fullUrl: String
    ): UserProfileImageResponse

    @GET
    suspend fun getRiwayatTarikSimp(
        @Url fullUrl: String
    ): RiwayatTarikSimpananResponse

    @GET
    suspend fun getTarikSimpananInfo(
        @Url fullUrl: String
    ): TarikSimpananProcessedResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${ApiRemoteCode.POST_TARIK_SIMPANAN}")
    suspend fun postTarikSimpanan(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): TarikNominalSimpananResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${ApiRemoteCode.POST_UPDATE_USER_PROFILE}")
    suspend fun updateProfileData(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): UpdateProfileResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${ApiRemoteCode.POST_HITUNG_ADM_PINJAMAN}")
    suspend fun hitungAdmPinjaman(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): HitungAdmPinjamanResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${ApiRemoteCode.POST_AJUKAN_PINJAMAN}")
    suspend fun ajukanPinjaman(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): AjukanPinjamanResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=${ApiRemoteCode.POST_UPDATE_PERSONAL_DATA_USER}")
    suspend fun editUserPersonalData(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): EditPersonalDataResponse
}