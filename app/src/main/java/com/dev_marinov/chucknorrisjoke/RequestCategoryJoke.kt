package com.dev_marinov.chucknorrisjoke

import android.util.Log
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header

class RequestCategoryJoke() {

    fun getCategory(activity: MainActivity?) {

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
                    (activity as MainActivity).arrayList.add(item.toString())
                }

                    // интерфейс передает команду в FragmentList, что категорию мы получили
                    myInterFaceAdapterUpdate.methodMyInterFaceAdapterUpdate()
            }
        })

    }

    interface MyInterFaceAdapterUpdate {
        fun methodMyInterFaceAdapterUpdate()
    }
    fun setMyInterFaceAdapterUpdate(myInterFaceAdapterUpdate: MyInterFaceAdapterUpdate) {
        RequestCategoryJoke.myInterFaceAdapterUpdate = myInterFaceAdapterUpdate
    }
    companion object { // статический интерфейс
        lateinit var myInterFaceAdapterUpdate: RequestCategoryJoke.MyInterFaceAdapterUpdate
    }


}