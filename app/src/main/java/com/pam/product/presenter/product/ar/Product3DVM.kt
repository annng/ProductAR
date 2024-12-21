package com.pam.product.presenter.product.ar

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.filament.Engine
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import java.io.File

class Product3DVM() : ViewModel() {



    fun loadModel(context: Context, file: File, engine: Engine, onLoaded: (ModelRenderable) -> Unit, onError: (Throwable) -> Unit) {
        val renderableSource = RenderableSource.builder()
            .setSource(context, Uri.fromFile(file), RenderableSource.SourceType.GLTF2)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        ModelRenderable.builder()
            .setSource(context, renderableSource)
            .build(engine)
            .thenAccept(onLoaded)
            .exceptionally {
                onError(it)
                null
            }
    }
}