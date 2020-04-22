package com.example.nikechallenge.network

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UDRetrofitTest{
    lateinit var udRetrofit: UDRetrofit

    @Before
    fun setUp() {
         udRetrofit = UDRetrofit()
    }

    @Test
    fun getSchools() {
        val response = udRetrofit.getDefinitionResponse("wut").blockingFirst()
        assertNotNull(response)
        assertNotEquals(response.list.size, 0)
    }
}