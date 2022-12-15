package com.thatnawfal.githubuser.presentation.logic

import com.thatnawfal.githubuser.data.network.service.ApiClient
import com.thatnawfal.githubuser.utils.Helper
import junit.framework.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit

class UserViewModelTest {

    //Retrofit Instance
    @Test
    fun `base url checking`(){
        val instances: Retrofit = ApiClient.retrofit
        assertEquals(instances.baseUrl().toUrl().toString(), Helper.api_endpoint)
    }

    // Api
    @Test
    fun `get api responsed`(){

    }
}