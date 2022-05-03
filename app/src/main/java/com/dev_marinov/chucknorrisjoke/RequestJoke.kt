package com.dev_marinov.chucknorrisjoke

import android.util.Log
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject

class RequestJoke {

    fun getJoke(category: String) {

        val asyncHttpClient: AsyncHttpClient = AsyncHttpClient()
        val requestParams: RequestParams = RequestParams()
        requestParams.put("category", category )

        asyncHttpClient.get("https://api.chucknorris.io/jokes/random?", requestParams, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                Log.e("333", "-onFailure=" + responseString)
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                Log.e("333", "-onSuccessJoke=" + responseString)
                try {
                    val jsonObject: JSONObject = JSONObject(responseString)
                    val value: String = jsonObject.getString("value")

                    // интерфейс передает шутку в FragmentList
                    myInterFaceSetJoke.methodMyInterFaceSetJoke(value)
                }
                catch (e: JSONException) {
                    Log.e("333", "-try catch=" + e)
                }
            }
        })
    }


    interface MyInterFaceSetJoke {
        fun methodMyInterFaceSetJoke(value: String)
    }
    fun setMyInterFaceSetJoke(myInterFaceSetJoke: MyInterFaceSetJoke) {
        RequestJoke.myInterFaceSetJoke = myInterFaceSetJoke
    }
    companion object { // статический интерфейс
        lateinit var myInterFaceSetJoke: RequestJoke.MyInterFaceSetJoke
    }
}