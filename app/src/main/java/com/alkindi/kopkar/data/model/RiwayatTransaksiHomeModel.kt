package com.alkindi.kopkar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RiwayatTransaksiHomeModel(
    val jenisTransaksi: String,
    val tipeTransaksi: String,
    val status: Int,
    val tglTransaksi: String,
):Parcelable