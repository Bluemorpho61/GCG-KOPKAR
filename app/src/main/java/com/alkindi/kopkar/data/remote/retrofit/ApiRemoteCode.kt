package com.alkindi.kopkar.data.remote.retrofit

object ApiRemoteCode {

    //    Untuk fitur personal data user
    const val GET_USER_PERSONAL_DATA =
        "gS%2BZtyMBHTdgEoheRgK6hoGn9gB9jdSeepx4X6/t2uDtvQTu57s32w%3D%3D"

    //    Untuk laman user profile
    const val GET_USER_PROFILE_INFO = "pfLRnrcSVEGRTOAUh6gjc7eh/mvQzehqG4PdElhk/ho%3D"

    //    Dipake bwt laman home
    const val GET_TOTALPINJAMAN_USER =
        "RMULh8EX7OeRTOAUh6gjc0fTS6w6yarnNmGlUghIFK8vvOgHSm1Qlw%3D%3D"

    //    Untuk fitur pinjaman
    const val GET_LIST_TIPE_PINJAMAN =
        "tBuYtyWkt9B8koMhFgqarQTnRtBFfK6yOVCvbjyXGbm6Jx7LO9FeFklDkiCsPfah"

    //    Untuk fitur tarik simpanan
    const val GET_DETAIL_TARIKSIMP_USER =
        "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6yTAxuvzoHj/kLVDcoWjRkzorcABlyx4eL"

    const val GET_HISTORY_TARIKSIMP_USER =
        "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6yMhf%2B8XnZd799HXV35pDBeAmaQOPnZORD"

    const val GET_SISA_TARIKSIMP_USER =
        "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6ykbo7yZ7uRy8JmkDj52TkQw%3D%3D"

    const val GET_RIWAYAT_TRANSAKSI_HOME =
        "RMULh8EX7OeRTOAUh6gjczLzlgxjU9l1ptFYPDEmI32mLptn1Yft3g%3D%3D"

    const val GET_USER_IMG = "RMULh8EX7OeRTOAUh6gjc1BGjyV1sPvjL9A83ffnZns%3D"

    const val GET_HISTORY_PINJAMAN = "UQihbFj9rzRUfDAg3HcRNwTnRtBFfK6yQIvGZwXDorZcCSU/ZzLMIA%3D%3D"

    const val GET_DETAIL_HISTORY_PINJAMAN =
        "UQihbFj9rzRUfDAg3HcRNwTnRtBFfK6yTAxuvzoHj/kGFdvAvYTsqNHpCTCNy17rSUOSIKw99qE%3D"

    const val GET_DETAIL_PENARIKAN_SIMPANAN =
        "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6yvvm9ufr2xeN6vyfZWym3iy2SHI5OALHfN3vYxpewHH8%3D"

    const val POST_HITUNG_ADM_PINJAMAN = "tBuYtyWkt9B8koMhFgqarQTnRtBFfK6yd3qgnOiweQo%3D"

    const val POST_TARIK_SIMPANAN =
        "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6ysmeazVkvQDybYeXXFYlJNFoArdrRosYg"

    const val POST_AJUKAN_PINJAMAN =
        "tBuYtyWkt9B8koMhFgqarQTnRtBFfK6yl7wmBxpAUFciFOPy4v4dPQmaQOPnZORD"

    const val POST_UPDATE_USER_PROFILE =
        "pfLRnrcSVEGRTOAUh6gjcw%2B3BfUv3TeN10CyHSkUyR8%3D"

    const val POST_UPDATE_PERSONAL_DATA_USER =
        "gS%2BZtyMBHTdgEoheRgK6hpiC0koiixuPdMRrFD8wcA/ok1VvRGdRugmaQOPnZORD"

    fun apiUrlArranger(
        baseUrl: String,
        apiDevCode: String,
        apiWorkspaceCode: String,
        apiRemoteCode: String,
        vars: String?
    ): String {
        return if (vars.isNullOrEmpty()) {
            "${baseUrl}txn?fnc=runLib;opic=${apiDevCode};csn=${apiWorkspaceCode};rc=${apiRemoteCode}"
        } else {
            "${baseUrl}txn?fnc=runLib;opic=${apiDevCode};csn=${apiWorkspaceCode};rc=${apiRemoteCode};vars=${vars}"
        }
    }
}

