package com.pam.product.presenter.product.detail

import com.pam.product.domains.repository.ProductRepository

class ProductDetailUseCase(
    private val productRepository: ProductRepository
) {
    suspend fun downloadModel(url: String) = productRepository.downloadFile(url)
}