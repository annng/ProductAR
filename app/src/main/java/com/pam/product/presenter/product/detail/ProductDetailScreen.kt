package com.pam.product.presenter.product.detail

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.pam.product.core.route.NavigationDestinations
import com.pam.product.models.Product
import com.pam.product.presenter.product.ar.model.ProductModelScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProductDetailScreen(
    navController: NavController,
    product: Product,
    viewModel: ProductDetailVM = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val nestedScrollConnection = remember { object : NestedScrollConnection {} }

    val isSpace3DActive = remember {
        MutableTransitionState(false)
    }

    val isThumbnail = remember {
        MutableTransitionState(true)
    }

    val isTouchingScene = remember {
        mutableStateOf(false)
    }

    Scaffold(modifier = modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .nestedScroll(nestedScrollConnection)
                .verticalScroll(scrollState, !isTouchingScene.value)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                androidx.compose.animation.AnimatedVisibility(visibleState = isThumbnail) {
                    AsyncImage(
                        product.thumbnail, "thumbnail ${product.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(16.dp)
                            )
                            .aspectRatio(3 / 4f),
                        contentScale = ContentScale.Crop,
                    )
                }

                androidx.compose.animation.AnimatedVisibility(visibleState = isSpace3DActive) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3 / 4f)
                            ,
                    ) {
                        ProductModelScreen(
                            navController,
                            product,
                            uiState.value.fileModel?.path ?: "",
                            onTouchScene = {
                                // Disable scrolling when touching the Scene area
                                isTouchingScene.value = true
                            }
                        )
                    }
                }

                Text(
                    "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = .8f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopEnd),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            // Disable scrolling when touching the Scene area
                            isTouchingScene.value = false
                            tryAwaitRelease()
                        }
                    )
                },
            ) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .padding(16.dp),
                        shape = RoundedCornerShape(50),
                        onClick = {
                            viewModel.setView3D(ModelEnv.REAL)
                            if (!viewModel.checkFileExist(product.model3d!!)) {
                                product.model3d.let { viewModel.downloadFile(it) }
                            }
                        }) {
                        Text(
                            "View 3D Real", style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(16.dp),
                        shape = RoundedCornerShape(50),
                        onClick = {
                            viewModel.setView3D(ModelEnv.SPACE)
                            if (!viewModel.checkFileExist(product.model3d!!)) {
                                product.model3d.let { viewModel.downloadFile(it) }
                            }
                        }) {
                        Text(
                            "View 3D Models", style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                if (uiState.value.isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Sedang mengunduh 3D model...",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                Text(LoremIpsum().values.iterator().next())
            }

        }
    }

    isSpace3DActive.targetState =
        uiState.value.fileModel != null && viewModel.checkFileExist(product.model3d!!) && uiState.value.view3D == ModelEnv.SPACE
    isThumbnail.targetState = uiState.value.view3D != ModelEnv.SPACE

    LaunchedEffect(uiState.value.view3D) {
        if (uiState.value.fileModel != null && viewModel.checkFileExist(product.model3d!!) && uiState.value.view3D != ModelEnv.IDLE) {
            when (uiState.value.view3D) {
                ModelEnv.REAL -> {
                    navigateToARScreen(navController, uiState.value.fileModel?.path)
                    viewModel.setView3D(env = ModelEnv.IDLE)
                }
//                ModelEnv.SPACE -> navigateToModelScreen(
//                    navController,
//                    uiState.value.fileModel?.path
//                )
                else -> {}
            }

        }
    }
}

fun navigateToARScreen(navController: NavController, filePath: String?) {
    navController.currentBackStackEntry?.savedStateHandle?.set(
        "product_model",
        filePath
    )
    navController.navigate(NavigationDestinations.ProductArRoute.route)
}

fun navigateToModelScreen(navController: NavController, filePath: String?) {
    navController.currentBackStackEntry?.savedStateHandle?.set(
        "product_model",
        filePath
    )
    navController.navigate(NavigationDestinations.ProductModelRoute.route)
}