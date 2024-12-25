package com.pam.product.domains.repository

import android.content.Context
import com.pam.product.domains.remote.ProductService

class ProductRepository(
    private val productService: ProductService,
    private val context : Context
) {

    suspend fun getProducts() = productService.getProducts()

    suspend fun downloadFile(url : String) = productService.downloadFile(url, context)
}