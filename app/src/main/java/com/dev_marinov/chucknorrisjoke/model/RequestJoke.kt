package com.dev_marinov.chucknorrisjoke.model

import android.util.Log
import com.dev_marinov.chucknorrisjoke.presentation.FragmentList
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject

object RequestJoke {

    fun getJoke(category: String) {

        val asyncHttpClient = AsyncHttpClient()
        val requestParams = RequestParams()
        requestParams.put("category", category )

        asyncHttpClient.get("https://api.chucknorris.io/jokes/random?", requestParams, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                Log.e("333", "-onFailure=" + responseString)
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                Log.e("333", "-onSuccessJoke=" + responseString)
                try {
                    val jsonObject: JSONObject = JSONObject(responseString)
                    val valueJoke: String = jsonObject.getString("value")

                    // интерфейс передает шутку в FragmentList
                        FragmentList.myInterFaceJoke.methodMyInterFaceJoke(valueJoke)

                }
                catch (e: JSONException) {
                    Log.e("333", "-try catch=" + e)
                }
            }
        })
    }



}