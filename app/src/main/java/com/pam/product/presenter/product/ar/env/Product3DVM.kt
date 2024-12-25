package com.pam.product.presenter.product.ar.env

import androidx.lifecycle.ViewModel
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Product3DVM() : ViewModel() {

    private val _modelNode = MutableStateFlow<ModelNode?>(null)
    val modelNode: StateFlow<ModelNode?> = _modelNode




}