package com.alkindi.kopkar.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserModel(
    val username: String,
    val password: String,
    val isLogin: Boolean
)

@Parcelize
data class SimpTypeWNominal(
    val nominal: String
) : Parcelable

@Parcelize
data class PinjDocnum(
    val docNum: String
) : Parcelable

@Parcelize
data class ProcessedTarikSimp(
    val docnum: String?
) : Parcelable

@Parcelize
data class UserProfile(
    val namaUser: String?,
    val emailUser: String?,
    val noHPUser: String?
) : Parcelable

@Parcelize
data class InputtedBiayaPot(
    val tipePinjaman: String,
    val tipePotongan: String,
    val nomTipePot: String,
    val nomPotPri: String,
    val noAtasan: String,
    val jmlTenor: String,
    val tanggalPencairan: String?,
    val tanggalBonus: String?,
    val nominalPinjaman: String?
) : Parcelable


@Parcelize
data class ProcessedCalculation(
    val amp: String,
    val totam: String,
    val aminst: String,
    val amjasa: String,
    val intadm: String,
    val amAdm: String,
    val plafon: String,
    val nominalPinjaman: String,
    val tipePotongan: String,
    val tenor: String
) : Parcelable