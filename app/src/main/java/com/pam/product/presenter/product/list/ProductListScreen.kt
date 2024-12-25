package com.pam.product.presenter.product.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.pam.product.core.ext.toDash
import com.pam.product.core.route.NavigationDestinations
import com.pam.product.models.Product
import com.pam.product.models.State
import com.pam.product.ui.theme.Caption
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductListVM = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    val products = uiState.value.products

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AR Product") })
        }
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {

            if (uiState.value.state == State.LOADING) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.value.state == State.ERROR) {
                Text(
                    uiState.value.error, style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(products) { index, data ->
                    ProductItemView(modifier = Modifier.clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set("product", data)
                        navController.navigate(NavigationDestinations.ProductDetailRoute.route)
                    }, product = data)
                }
            }

        }
    }

}

@Composable
fun ProductItemView(modifier: Modifier = Modifier, product: Product) {
    Row(
        modifier = modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        AsyncImage(
            product.thumbnail, contentDescription = product.name, contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .size(76.dp)
        )

        Spacer(
            modifier = Modifier.width(16.dp)
        )


        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                product.name.toDash(), style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                product.price.toString(), style = MaterialTheme.typography.bodySmall.copy(
                    color = Caption
                )
            )
        }
    }
}