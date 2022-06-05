package com.dev_marinov.chucknorrisjoke.model.categories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ретрофит базовый урл
class RetroCategoriesInstance {

    companion object {

        fun getRetroCategory(): Retrofit {
            val baseUrl = "https://api.chucknorris.io/jokes/"
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
//              .create(MyApi::class.java)
        }
    }
}