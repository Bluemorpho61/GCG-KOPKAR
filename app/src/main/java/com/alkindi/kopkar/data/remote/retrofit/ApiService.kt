package com.alkindi.kopkar.data.remote.retrofit

import com.alkindi.kopkar.data.remote.response.AjukanPinjamanLainResponse
import com.alkindi.kopkar.data.remote.response.AjukanPinjamanResponse
import com.alkindi.kopkar.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.response.DetailSimpananResponse
import com.alkindi.kopkar.data.remote.response.EditPersonalDataResponse
import com.alkindi.kopkar.data.remote.response.ExtUserProfileResponse
import com.alkindi.kopkar.data.remote.response.HistoryPinjamanResponse
import com.alkindi.kopkar.data.remote.response.HistoryTarikSimpResponse
import com.alkindi.kopkar.data.remote.response.HitungAdmPinjamanLainResponse
import com.alkindi.kopkar.data.remote.response.HitungAdmPinjamanResponse
import com.alkindi.kopkar.data.remote.response.LoginResponse
import com.alkindi.kopkar.data.remote.response.NominalSimpananResponse
import com.alkindi.kopkar.data.remote.response.PersonalDataResponse
import com.alkindi.kopkar.data.remote.response.RiwayatTarikSimpananResponse
import com.alkindi.kopkar.data.remote.response.RiwayatTransaksiResponse
import com.alkindi.kopkar.data.remote.response.TarikNominalSimpananResponse
import com.alkindi.kopkar.data.remote.response.TenorListResponse
import com.alkindi.kopkar.data.remote.response.TipePotonganResponse
import com.alkindi.kopkar.data.remote.response.TotalPinjamanResponse
import com.alkindi.kopkar.data.remote.response.UpdateProfileResponse
import com.alkindi.kopkar.data.remote.response.UserProfileImageResponse
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
        @Url fullUrl: String = ApiConfig.BASE_URL_KOPKAR
    ): ExtUserProfileResponse

    @GET
    suspend fun getPersonal(
        @Url fullUrl: String
    ): PersonalDataResponse

    @GET
    suspend fun getHistorySimpanan(
        @Url fullUrl: String
    ): List<HistoryTarikSimpResponse>

    @GET
    suspend fun getNominalSimpananUser(
        @Url fullUrl: String
    ): NominalSimpananResponse

    @GET
    suspend fun getTipePotonganUser(
        @Url fullUrl: String
    ): TipePotonganResponse

    @GET
    suspend fun getHistoryPinjaman(
        @Url fullUrl: String
    ): HistoryPinjamanResponse

    @GET
    suspend fun getDetailSimpanan(
        @Url fullUrl: String
    ): DetailSimpananResponse

    @GET
    suspend fun getTenorList(
        @Url fullUrl: String
    ): TenorListResponse

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

    @FormUrlEncoded
    @POST("https://kopegmar.gcgakor.id/txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6y6OVw0Q2/ZWSE2T%2BDBSLsen/SgBttLGZS")
    suspend fun postTarikSimpanan(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): TarikNominalSimpananResponse

    @FormUrlEncoded
    @POST("https://kopegmar.gcgakor.id/txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=KvRnqbr%2Bktu7HRDvQttp6MRyn3VICeItrNiEgAa5Ce0%3D")
    suspend fun updateProfileData(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): UpdateProfileResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=tBuYtyWkt9DJpiePfo46tATnRtBFfK6yd3qgnOiweQo%3D")
    suspend fun hitungAdmPinjaman(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): HitungAdmPinjamanResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=tBuYtyWkt9DJpiePfo46tATnRtBFfK6yK6ChYzspyElabeFBWEFWHoPNmn8uf4HQ")
    suspend fun ajukanPinjaman(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): AjukanPinjamanResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=tBuYtyWkt9DJpiePfo46tATnRtBFfK6yeFEM8SP96ycJmkDj52TkQw%3D%3D")
    suspend fun hitungAdmPinjamanLain(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): HitungAdmPinjamanLainResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=tBuYtyWkt9DJpiePfo46tATnRtBFfK6yK6ChYzspyElabeFBWEFWHoDRiFVzSWeNxBeogXA5IyQ%3D")
    suspend fun ajukanPinjamanLain(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): AjukanPinjamanLainResponse

    @FormUrlEncoded
    @POST("${ApiConfig.BASE_URL_KOPKAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPKAR};csn=${ApiConfig.WORKSPACE_CODE_KOPKAR};rc=gS%2BZtyMBHTdgEoheRgK6hpiC0koiixuPdMRrFD8wcA/ok1VvRGdRugmaQOPnZORD")
    suspend fun editUserPersonalData(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): EditPersonalDataResponse
}