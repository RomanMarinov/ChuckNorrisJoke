package com.dev_marinov.chucknorrisjoke.model

import android.util.Log
import com.dev_marinov.chucknorrisjoke.presentation.FragmentList
import com.dev_marinov.chucknorrisjoke.presentation.MainActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header

object RequestCategoryJoke {

    var arrayListCategory: ArrayList<String> = ArrayList()

    fun getCategory() {

        val asyncHttpClient: AsyncHttpClient = AsyncHttpClient();
        asyncHttpClient.get("https://api.chucknorris.io/jokes/categories", null, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                Log.e("333", "-onFailure=" + responseString)
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                Log.e("333", "-onSuccess=" + responseString)

                val myString1 = responseString?.replace("\"","") // удалить кавычки из строки
                val myString2 = myString1?.replace("[", "") // удалить левую скобку из строки
                val myString3 = myString2?.replace("]", "") // удалить правую скобку из строки

                val myResponse: ArrayList<String> = myString3?.split(",") as ArrayList<String>
                Log.e("333", "-myResponse=" + myResponse)

                for (item in myResponse) {
                    arrayListCategory.add(item.toString())
                }

                    // интерфейс передает команду в FragmentList, что категорию мы получили
                    // и надо обновить адаптер
                    FragmentList.myInterFaceCategory.methodMyInterFaceCategory()
            }
        })

    }

    fun myCategory() = arrayListCategory

}