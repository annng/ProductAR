package com.pam.product.core.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.pam.product.models.Product
import com.pam.product.presenter.product.ar.Product3DScreen
import com.pam.product.presenter.product.detail.ProductDetailScreen
import com.pam.product.presenter.product.list.ProductListScreen

fun NavController.isCanBack(previousRoute: String): Boolean {
    val previousEntry = this.previousBackStackEntry

    return previousEntry != null && previousEntry.destination.route == previousRoute
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationDestinations.ProductRootRoute.route) {
        productGraph(navController)
    }
}

fun NavGraphBuilder.productGraph(navController: NavController) {
    navigation(startDestination = NavigationDestinations.ProductRootRoute.route, route = NavigationDestinations.ProductListRoute.route) {
        composable(NavigationDestinations.ProductListRoute.route) { ProductListScreen(navController) }
        composable<Product>(NavigationDestinations.ProductDetailRoute.route) { backStackEntry ->
            val product: Product = backStackEntry.toRoute()
            ProductDetailScreen(navController, product) }
        composable(NavigationDestinations.ProductArRoute.route) { Product3DScreen(navController) }
    }
}
