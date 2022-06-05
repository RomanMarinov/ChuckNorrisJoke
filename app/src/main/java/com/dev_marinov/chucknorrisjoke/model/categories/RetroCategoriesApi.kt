package com.dev_marinov.chucknorrisjoke.model.categories

import retrofit2.Response
import retrofit2.http.GET

// интерфейс апи для получения массива категорий для шуток
// корутин suspend для работы с асинхронноым запросом
interface RetroCategoriesApi {

    @GET("categories")
    suspend fun getCategories(): Response<ArrayList<String>>

}