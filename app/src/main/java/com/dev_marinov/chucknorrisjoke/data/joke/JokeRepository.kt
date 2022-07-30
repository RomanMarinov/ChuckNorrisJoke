package com.dev_marinov.chucknorrisjoke.data.joke

import android.util.Log
import com.dev_marinov.chucknorrisjoke.data.joke.remote.JokeService
import com.dev_marinov.chucknorrisjoke.domain.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeRepository @Inject constructor(private val remoteDataSource: JokeService) {

    suspend fun getJoke(category: Category): String {
        Log.e("333","=suspend fun getJoke=")
        val response = remoteDataSource.getJoke(category = category.name)
        val joke = response.body()?.let { it.value } ?: ""
        return joke
    }
}
