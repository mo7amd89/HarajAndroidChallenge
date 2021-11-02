package com.example.harajtask.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HarajReopImplTest {

    lateinit var contex :Context
    lateinit var reopImplTest: HarajReopImpl

    @Test
    fun testGetProducts() {
        val kk=reopImplTest.getProducts()
        assert(kk.isNotEmpty())
    }
    @Before
    fun testGetContext() {
        contex=ApplicationProvider.getApplicationContext()
        reopImplTest=HarajReopImpl(contex)
    }
}