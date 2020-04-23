package com.example.nikechallenge.network

import com.example.nikechallenge.model.UDResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class UDRepository {
    fun getDefinitionsList(word: String) : Observable<UDResponse>{
        return UDRetrofit
            .getDefinitionResponse(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}