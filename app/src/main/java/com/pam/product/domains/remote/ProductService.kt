package com.pam.product.domains.remote

import android.content.Context
import android.util.Log
import com.pam.product.models.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class ProductService(private val client: HttpClient) {

    suspend fun getProducts(): List<Product> {
        return client.get("/product.json").body()
    }

    suspend fun downloadFile(url: String, context: Context): File {
        val fileName = url.substring(url.lastIndexOf('/') + 1)

        val file = File(context.cacheDir, fileName)
        withContext(Dispatchers.IO) {
            val urlConnection = URL(url).openConnection() as HttpURLConnection
            urlConnection.inputStream.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
        }
        Log.e("saveFile", file.path)
        Log.e("fileName", fileName)
        return file
    }
}