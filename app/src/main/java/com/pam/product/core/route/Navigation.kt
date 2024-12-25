package com.pam.product.core.route

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.pam.product.models.Product
import com.pam.product.presenter.product.ar.env.Product3DScreen
import com.pam.product.presenter.product.ar.model.ProductModelScreen
import com.pam.product.presenter.product.detail.ProductDetailScreen
import com.pam.product.presenter.product.list.ProductListScreen

fun NavController.isCanBack(previousRoute: String): Boolean {
    val previousEntry = this.previousBackStackEntry

    return previousEntry != null && previousEntry.destination.route == previousRoute
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.ProductRootRoute.route
    ) {
        productGraph(navController)
    }
}

fun NavGraphBuilder.productGraph(navController: NavController) {
    navigation(
        startDestination = NavigationDestinations.ProductListRoute.route,
        route = NavigationDestinations.ProductRootRoute.route
    ) {
        composable(NavigationDestinations.ProductListRoute.route) { ProductListScreen(navController) }
        composable(NavigationDestinations.ProductDetailRoute.route) { backStackEntry ->
            val product =
                navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
            product?.let {
                ProductDetailScreen(navController, it)
            }
        }
        composable(NavigationDestinations.ProductArRoute.route) { backStackEntry ->
            val model =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("product_model")
            val product =
                navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")

            model?.let {
                Product3DScreen(navController, it, product)
            }
        }
        composable(NavigationDestinations.ProductModelRoute.route) { backStackEntry ->
            val model =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("product_model")
            val product =
                navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")

            model?.let {
                ProductModelScreen(navController, product, it){

                }
            }
        }
    }
}
