package com.dev_marinov.chucknorrisjoke.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.chucknorrisjoke.model.joke.RetroJokeApi
import com.dev_marinov.chucknorrisjoke.model.joke.RetroJokeInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelJoke : ViewModel(){

    private var joke: MutableLiveData<String> = MutableLiveData()

    fun getJokeObserver(): MutableLiveData<String> {
        return joke
    }

    fun makeApiCall(category: String) {
        Log.e("333","=makeApiCall=")

        // GlobalScope - карутин верхнего уровня и живет столько сколько живет все приложение
        // при этом если фрагмент или активность будут уничтожены где мы используем GlobalScope
        // то GlobalScope не будет уничтожен и это может привести к утечке памяти
        // Поэтому карутин должен привязан к жизненому циклу (к опреденному компоненту).
        // lifecycleScope для фрагментов, viewModelScope - для viewModel

        viewModelScope.launch(Dispatchers.IO){

            val retroInstance = RetroJokeInstance.getRetroJoke().create(RetroJokeApi::class.java)
            val response = retroInstance.getJoke(category)
            if (response.isSuccessful) {

                joke.postValue(response.body()!!.value)
            }
        }

    }



}