package com.example.nikechallenge.network

import org.junit.Assert.*
import org.junit.Test

class UDRetrofitTest{
    @Test
    fun getSchools() {
        val response = UDRetrofit.getDefinitionResponse("wut").blockingFirst()
        assertNotNull(response)
        assertNotEquals(response.list.size, 0)
    }
}