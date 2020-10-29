package com.rukka.somecats.network.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewCats(
    val title: String,
    val cats: List<Cat>
) : Parcelable