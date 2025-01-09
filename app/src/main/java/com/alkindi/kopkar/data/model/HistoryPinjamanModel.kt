package com.alkindi.kopkar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class HistoryPinjamanModel(
    val uniqueId: String,
    val nominalGaji: String,
    val pinjamanBulanan: String,
    val tenor: String
) : Parcelable