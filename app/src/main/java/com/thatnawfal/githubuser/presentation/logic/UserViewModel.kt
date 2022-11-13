package com.thatnawfal.githubuser.presentation.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thatnawfal.githubuser.data.model.response.DetailUsersModel
import com.thatnawfal.githubuser.data.model.response.SearchResponse
import com.thatnawfal.githubuser.data.model.response.UsersModel
import com.thatnawfal.githubuser.data.network.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    companion object {
        const val TAG = "UserViewModel : "
    }

    private val _defaultList = MutableLiveData<List<UsersModel>>()
    val defaultList : LiveData<List<UsersModel>> = _defaultList

    private val _listUsers = MutableLiveData<List<UsersModel>>()
    val listUsers : LiveData<List<UsersModel>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<DetailUsersModel>()
    val user : LiveData<DetailUsersModel> = _user

    init {
        loadUsers()
    }

    private fun loadUsers() {
        _isLoading.value = true
        val client = ApiClient.instances().getUsers(10)

        client.enqueue(object : Callback<List<UsersModel>> {
            override fun onResponse(call: Call<List<UsersModel>>, response: Response<List<UsersModel>>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _defaultList.value = response.body() as List<UsersModel>
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersModel>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun searchUsers(username: String, per_page : Int = 0){
        _isLoading.value = true
        val client = ApiClient.instances().searchUsers(username, per_page)

        client.enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _listUsers.value = response.body()?.items as List<UsersModel>
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun detailUser(username: String){
        _isLoading.value = true
        val client = ApiClient.instances().getDetailUser(username)

        client.enqueue(object: Callback<DetailUsersModel>{
            override fun onResponse(
                call: Call<DetailUsersModel>,
                response: Response<DetailUsersModel>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUsersModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun emptySearchField(){
        _defaultList.value = defaultList.value
    }
}