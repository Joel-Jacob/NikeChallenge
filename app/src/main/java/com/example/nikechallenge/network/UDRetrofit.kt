package com.example.nikechallenge.network

import com.example.nikechallenge.model.UDResponse
import com.example.nikechallenge.util.Constants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UDRetrofit {
    var uDService: UDService

    init {
        uDService = getService(getInstance())
    }

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getService(retrofit: Retrofit): UDService {
        return retrofit.create(UDService::class.java)
    }

    fun getDefinitionResponse(word: String): Observable<UDResponse> {
        return uDService.getDefinition(Constants.API_KEY, word)
    }
}