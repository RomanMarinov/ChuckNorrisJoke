package com.dev_marinov.chucknorrisjoke.data.joke.remote

import com.dev_marinov.chucknorrisjoke.domain.Joke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeService {

    @GET("random")
    suspend fun getJoke(
        @Query("category")category: String
    ): Response<Joke>

}