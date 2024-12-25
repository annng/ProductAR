package com.pam.product.presenter.product.list

import com.pam.product.models.Product
import com.pam.product.models.State
import kotlin.io.path.OnErrorResult

data class ProductListUiState(
    val state : State = State.IDLE,
    val products : List<Product> = emptyList(),
    val error: String = ""
)