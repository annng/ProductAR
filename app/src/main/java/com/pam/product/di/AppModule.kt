package com.pam.product.di

import android.content.Context
import com.pam.product.core.api.KtorClientProvider
import com.pam.product.domain.remote.ProductService
import com.pam.product.domain.repository.ProductRepository
import com.pam.product.presenter.product.list.ProductListUseCase
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// Define the app's dependencies
val appModule = module {
    single<Context> { androidContext() }

    apiClient
    service
    repository
    useCases

//    viewModel { ProductListVM(get()) }
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
}