package com.example.practice

import okhttp3.MultipartBody
import retrofit2.http.*

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val link: String,
    val image_url: String,
    val rating: String,
    val review_link: String,
    val eco_check: Boolean,
    val eco_info: List<EcoInfo>
)

data class EcoInfo(
    val title: String,
    val link: String,
    val snippet: String
)

interface ProductApi {
    @GET("/")
    suspend fun getProducts(): List<Product>

    @GET("/{name}")
    suspend fun getProductsByName(@Path("name") name: String): List<Product>

    @GET("/{name}/{ecoCheck}")
    suspend fun getProductsByNameAndEcoCheck(
        @Path("name") name: String,
        @Path("ecoCheck") ecoCheck: Boolean
    ): List<Product>

    @GET("/{name}/true")
    suspend fun getTrueProductsByName(@Path("name") name: String): List<Product>

    @Multipart
    @POST("/upload-json/")
    suspend fun uploadJson(@Part file: MultipartBody.Part): Map<String, Any>
}
