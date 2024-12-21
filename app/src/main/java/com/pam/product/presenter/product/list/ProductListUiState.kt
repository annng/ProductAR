package com.pam.product.presenter.product.list

import com.pam.product.models.Product
import kotlin.io.path.OnErrorResult

data class ProductListUiState(
    val products : List<Product> = emptyList(),
    val error: String = ""
)