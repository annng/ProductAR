package com.pam.product.models


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Product(
    @SerialName("id")
    val id: Int?,
    @SerialName("model_3d")
    val model3d: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("price")
    val price: Double?,
    @SerialName("thumbnail")
    val thumbnail: String?
) : Parcelable