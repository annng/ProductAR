package com.pam.product.presenter.product.detail

import java.io.File

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val fileModel: File? = null,
    val view3D: ModelEnv = ModelEnv.IDLE
)

enum class ModelEnv {
    IDLE, REAL, SPACE
}