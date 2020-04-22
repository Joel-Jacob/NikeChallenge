package com.example.nikechallenge.network

import com.example.nikechallenge.model.UDResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UDService {

    @GET("/define")
    fun getDefinition(
        @Header("x-rapidapi-key") apiKey: String,
        @Query("term") word: String
    ): Observable<UDResponse>
}