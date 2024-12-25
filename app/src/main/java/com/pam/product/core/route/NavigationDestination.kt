package com.pam.product.core.route

sealed class NavigationDestinations(val route: String) {
    object ProductRootRoute : NavigationDestinations(route = "product")


    object ProductListRoute : NavigationDestinations(route = "product_list")
    object ProductDetailRoute : NavigationDestinations(route = "product_detail")
    object ProductArRoute : NavigationDestinations(route = "product_ar")
    object ProductModelRoute : NavigationDestinations(route = "product_model")
}