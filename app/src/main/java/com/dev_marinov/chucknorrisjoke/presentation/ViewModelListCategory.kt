package com.dev_marinov.chucknorrisjoke.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.chucknorrisjoke.model.categories.RetroCategoriesApi
import com.dev_marinov.chucknorrisjoke.model.categories.RetroCategoriesInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelListCategory : ViewModel() {

    var arrayList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    private val arrayListTemp: ArrayList<String> = ArrayList() // временный массив

    fun getArrayCategoryObserver(): MutableLiveData<ArrayList<String>> {
        return arrayList
    }

    fun makeApiCall() {
        Log.e("333","=makeApiCall=")

        // GlobalScope - карутин верхнего уровня и живет столько сколько живет все приложение
            // при этом если фрагмент или активность будут уничтожены где мы используем GlobalScope
            // то GlobalScope не будет уничтожен и это может привести к утечке памяти
            // Поэтому карутин должен привязан к жизненому циклу (к опреденному компоненту).
        // lifecycleScope для фрагментов, viewModelScope - для viewModel

        viewModelScope.launch(Dispatchers.IO){

            val retroInstance = RetroCategoriesInstance.getRetroCategory().create(RetroCategoriesApi::class.java)
            val response = retroInstance.getCategories()
            if (response.isSuccessful) {
                for (res in response.body()!!) {
                    arrayListTemp.add(res)
                }
                arrayList.postValue(arrayListTemp)
            }
        }

    }
}
