package com.info.uitc.freeandr.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Server(
    var countryName: String,
    var countryCode: String,
    var IP: String
) : Parcelable