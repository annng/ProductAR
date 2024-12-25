package com.pam.product.presenter.product.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class ProductDetailVM(
    private val useCase: ProductDetailUseCase,
    private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    fun downloadFile(url: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    fileModel = useCase.downloadModel(url),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
        }
    }

    fun checkFileExist(url: String): Boolean {
        val fileName = url.substring(url.lastIndexOf('/') + 1)
        val file = File(context.cacheDir, fileName)

        if (file.exists()){
            _uiState.value = _uiState.value.copy(fileModel = file)
        }

        return file.exists()
    }

    fun setView3D(env : ModelEnv){
        _uiState.value = _uiState.value.copy(view3D = env)
    }
}