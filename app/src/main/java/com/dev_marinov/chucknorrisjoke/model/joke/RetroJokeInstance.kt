package com.dev_marinov.chucknorrisjoke.model.joke

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ретрофит базовый урл
class RetroJokeInstance {

    companion object {

        fun getRetroJoke(): Retrofit {
                    val baseUrl = "https://api.chucknorris.io/jokes/"
                    return Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
      //                .create(MyApi::class.java)
        }
    }
}