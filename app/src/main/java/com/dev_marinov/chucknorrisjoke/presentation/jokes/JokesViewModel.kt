package com.dev_marinov.chucknorrisjoke.presentation.jokes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.chucknorrisjoke.data.category.CategoryRepository
import com.dev_marinov.chucknorrisjoke.domain.Category
import com.dev_marinov.chucknorrisjoke.data.joke.JokeRepository
import com.dev_marinov.chucknorrisjoke.presentation.model.SelectableItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val jokeRepository: JokeRepository
) : ViewModel() {

    var selectedPosition = 6
    private val DEFAULT_WIDTH = 213

    private val _widthTextViewCategory = MutableLiveData<Int>()
    val widthTextViewCategory = _widthTextViewCategory

    private val _categories: MutableLiveData<ArrayList<SelectableItem<Category>>> = MutableLiveData()
    val categories: LiveData<ArrayList<SelectableItem<Category>>> = _categories

    private val _joke: MutableLiveData<String> = MutableLiveData()
    val joke: LiveData<String> = _joke

    init {
        getCategories()

        categories.value?.let {
            if (it.isNotEmpty()) {
                val selectableCategory: SelectableItem<Category> = it[selectedPosition]
                getJoke(selectableCategory.mapToDomain())
            }
        }
    }

    fun onCategoryClick(position: Int, clickCategory: SelectableItem<Category>, widthTextViewCategory: Int) {
        selectedPosition = position
        updateCategories()
        getJoke(clickCategory.mapToDomain())
        _widthTextViewCategory.value = widthTextViewCategory
    }

    fun onCategoryClicked(category: SelectableItem<Category>) = getJoke(category.mapToDomain())

    private fun getJoke(category: Category) {
        Log.e("333","=getJoke=")
        viewModelScope.launch(Dispatchers.IO) {
            jokeRepository.getJoke(category).let {
                Log.e("333","=it=" + it)
                _joke.postValue(it)
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val list: ArrayList<SelectableItem<Category>> = ArrayList()
//            categoryRepository.getCategories().forEachIndexed { index, name ->
               // val category = SelectableCategory(name = name, isSelected = index == selectedPosition)


               // list.add(category)


            categoryRepository.getCategories().forEachIndexed { index, category ->
                val category1 = SelectableItem<Category>(item = category, isSelected = index == selectedPosition)
                list.add(category1)
            }
            _categories.postValue(list)
            _widthTextViewCategory.postValue(DEFAULT_WIDTH)
        }

        }

    private fun updateCategories() {
        val newCategories = arrayListOf<SelectableItem<Category>>()
        _categories.value?.let {
            it.forEachIndexed { index, category ->
                val newCategory = category.copy(isSelected = index == selectedPosition)
                newCategories.add(newCategory)
            }
        }
        _categories.value = newCategories
    }
}


