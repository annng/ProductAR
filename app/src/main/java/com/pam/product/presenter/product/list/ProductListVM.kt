package com.pam.product.presenter.product.list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.product.models.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListVM(
    private val useCase: ProductListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState


    init {
        fetchData()
    }

    private fun fetchData() {
        _uiState.value = _uiState.value.copy(
            state = State.LOADING
        )
        viewModelScope.launch {
            try {
                val data = useCase.getProducts()
                _uiState.value = _uiState.value.copy(
                    products = data,
                    state = State.SUCCESS
                )

            } catch (e: Exception) {
                Log.e("TAG", "$e")
                _uiState.value = _uiState.value.copy(
                    state = State.ERROR,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}