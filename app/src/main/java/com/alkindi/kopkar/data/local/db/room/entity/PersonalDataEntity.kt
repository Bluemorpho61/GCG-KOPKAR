package com.alkindi.kopkar.data.local.db.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class PersonalDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo("mbrid")
    var mbrid: String? = null,

    @ColumnInfo("nama")
    var nama: String? = null,

    @ColumnInfo("tgl_lahir")
    var tglLahir: String? = null,

    @ColumnInfo("religion")
    var religion: String? = null,

    @ColumnInfo("education")
    var education: String? = null,

    @ColumnInfo("mbrgroup")
    var mbrgroup: String? = null,

    @ColumnInfo("companyID")
    var companyID: String? = null,

    @ColumnInfo("company_begin")
    var companyBegin: String? = null,

    @ColumnInfo("company_last")
    var companyLast: String? = null,

    @ColumnInfo("tempat_lahir")
    var tempatLahir: String? = null,

    @ColumnInfo("alamat")
    var alamat: String? = null,

    @ColumnInfo("jenis_kelamin")
    var jenisKelamin: String? = null,

    @ColumnInfo("phone")
    var noHp: String? = null,

    @ColumnInfo("email")
    var email: String? = null,

    @ColumnInfo("description")
    var description: String? = null,

    @ColumnInfo("mbr_empno")
    var mbrEmpno: String? = null,

    @ColumnInfo("marital_status")
    var maritalStatus: String? = null,

    @ColumnInfo("mbr_empno2")
    var mbrEmpno2: String? = null,

    @ColumnInfo("mbr_unit")
    var mbrUnit: String? = null,

    @ColumnInfo("mbr_position")
    var mbrPosition: String? = null,

    @ColumnInfo("termination")
    var termination: String? = null,

    @ColumnInfo("term_type")
    var termType: String? = null,

    @ColumnInfo("term_reason")
    var termReason: String? = null,

    @ColumnInfo("term_eff")
    var termEff: String? = null,

    @ColumnInfo("ktp")
    var ktp: String? = null,

    @ColumnInfo("no_rekening")
    var noRek: String? = null,

    @ColumnInfo("nama_bank")
    var namaBank: String? = null,

    @ColumnInfo("nama_rekening")
    var namaRek: String? = null,

    @ColumnInfo("file_ktp")
    var fileKtp: String? = null,

    @ColumnInfo("file_slip")
    var fileSlip: String? = null,

    @ColumnInfo("file_sk")
    var fileSk: String? = null,

    @ColumnInfo("photo_file")
    var photoFile: String? = null,

    @ColumnInfo("created_info")
    var createdInfo: String? = null,

    @ColumnInfo("modified_info")
    var modifiedInfo: String? = null
) : Parcelable