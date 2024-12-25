package com.pam.product.di

import com.pam.product.core.api.KtorClientProvider
import com.pam.product.domains.remote.ProductService
import com.pam.product.domains.repository.ProductRepository
import com.pam.product.presenter.product.ar.env.Product3DVM
import com.pam.product.presenter.product.detail.ProductDetailUseCase
import com.pam.product.presenter.product.detail.ProductDetailVM
import com.pam.product.presenter.product.list.ProductListUseCase
import com.pam.product.presenter.product.list.ProductListVM
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// Define the app's dependencies
val appModule = module {
//    single<Context> { androidContext() }
}

val apiClient = module {
    single<HttpClient> { KtorClientProvider.client }
}
val service = module {
    single { ProductService(get()) }
}

val repository = module {

    single { ProductRepository(get(), get()) }
}

val useCases = module {
    single { ProductListUseCase(get()) }
    single { ProductDetailUseCase(get()) }
}

val viewModels = module {
    viewModel { ProductListVM(get()) }
    viewModel { ProductDetailVM(get(), get()) }
    viewModel { Product3DVM() }
}