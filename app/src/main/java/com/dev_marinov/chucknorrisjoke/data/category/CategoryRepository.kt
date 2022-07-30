package com.dev_marinov.chucknorrisjoke.data.category

import com.dev_marinov.chucknorrisjoke.data.category.remote.CategoryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(private val remoteDataSource: CategoryService) {

    suspend fun getCategories(): List<String> {
        val response = remoteDataSource.getCategories()
        val categories = response.body()?.let { it } ?: listOf<String>()
        return categories
    }
}