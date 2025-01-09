package com.alkindi.kopkar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RiwayatTransaksiModel(
    val jenisTransaksi: String,
    val idTransaksi: String,
    val nominal: String,
    val tglTransaksi: String
) : Parcelable