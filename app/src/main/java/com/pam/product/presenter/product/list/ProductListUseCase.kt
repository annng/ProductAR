package com.pam.product.presenter.product.list

import com.pam.product.domain.repository.ProductRepository

class ProductListUseCase(
    private val productRepository: ProductRepository
) {

    suspend fun getProducts() = productRepository.getProducts()

}