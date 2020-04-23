package com.example.nikechallenge.viewmodel

import com.example.nikechallenge.network.UDRetrofit
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UDViewModelTest{

    lateinit var uDRetrofit: UDRetrofit

    @Before
    fun setUp() {
        uDRetrofit = UDRetrofit()
    }

    @Test
    fun getResponse() {
        val response = uDRetrofit.getDefinitionResponse("wut").blockingFirst()
        assertNotNull(response)
        assertNotEquals(response.list.size, 0)
    }
}