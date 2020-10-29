package com.rukka.somecats.network.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cat(
    val id: Long,
    val title: String,
    val description: String,
    val images: List<String>
) : Parcelable