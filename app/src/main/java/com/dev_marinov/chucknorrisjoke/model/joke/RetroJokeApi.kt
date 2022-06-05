package com.dev_marinov.chucknorrisjoke.model.joke

import com.dev_marinov.chucknorrisjoke.data.ObjectJoke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// интерфейс апи для получения шутки
// корутин suspend для работы с асинхронноым запросом
// запрос с параметром
interface RetroJokeApi {

    @GET("random")
    suspend fun getJoke(
        @Query("category")category: String
    ): Response<ObjectJoke>

}