package com.pam.product.models


import com.squareup.moshi.Json
import kotlinx.serialization.Serializable


@Serializable
data class Product(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "model_3d")
    val model3d: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "price")
    val price: Double?,
    @Json(name = "thumbnail")
    val thumbnail: String?
)