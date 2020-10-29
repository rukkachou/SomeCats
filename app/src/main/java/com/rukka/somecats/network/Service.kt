package com.rukka.somecats.network

import com.rukka.somecats.network.entities.NewCats
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://my-json-server.typicode.com/rukkachou/somecats/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiService {
    @GET("new_cats")
    suspend fun getNewCats(): List<NewCats>
}

object Api {
    val retrofitService by lazy { retrofit.create(ApiService::class.java) }
}