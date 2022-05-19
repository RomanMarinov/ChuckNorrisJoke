package com.dev_marinov.chucknorrisjoke.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev_marinov.chucknorrisjoke.model.RequestCategoryJoke


class ViewModelListCategory : ViewModel() {

    private var arrayList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    //инициализируем список и заполняем его данными пользователей
    init {
        // с помощью value можно получить и отправить данные любым активным подписчикам
//        UserData.getData()
//        userList.value = UserData.getUsers()

        RequestCategoryJoke.getCategory()
        arrayList.value = RequestCategoryJoke.myCategory()


    }

    fun getArrayCategory() = arrayList


//    fun getListUsers() = userList

//    //для обновления списка передаем второй список пользователей
//    fun updateListUsers() {
//        userList.value = UserData.getAnotherUsers()
//    }
}

