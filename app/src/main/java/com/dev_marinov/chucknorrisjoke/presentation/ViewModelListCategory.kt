package com.dev_marinov.chucknorrisjoke.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev_marinov.chucknorrisjoke.model.RequestCategoryJoke


class ViewModelListCategory : ViewModel() {

    private var arrayList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    //инициализируем список и заполняем его данными пользователей
    init {

        RequestCategoryJoke.getCategory()
        arrayList.value = RequestCategoryJoke.myCategory()
    }

    fun getArrayCategory() = arrayList

}

