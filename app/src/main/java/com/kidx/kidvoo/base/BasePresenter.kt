package com.kidx.kidvoo.base

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BasePresenter {

    abstract fun onSuccessResponse(responseBody: Response<ResponseBody>)

    abstract fun onFailureResponse(responseBody: Response<ResponseBody>)


    fun getNewObserver(): Observer<Response<ResponseBody>> {
        return object: Observer<Response<ResponseBody>> {
            override fun onSubscribe(d: Disposable) {
                Log.d("onSubscribe", "Subscribed !!")
            }

            override fun onError(e: Throwable) {
                Log.d("onError", e.message.toString())
            }

            override fun onComplete() {
                Log.d("onComplete", "Completed !!")
            }

            override fun onNext(response: Response<ResponseBody>) {
                Log.d("onNext", "com.mycompany.coolkidapp.network.response.Response recieved  - " + response.code().toString())
                if(response.code() == 200) {
                    // Success
                    onSuccessResponse(response)
                } else {
                    // Failure
                    onFailureResponse(response)
                }
            }

        }
    }
}