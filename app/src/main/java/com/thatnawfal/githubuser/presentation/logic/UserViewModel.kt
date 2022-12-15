package com.thatnawfal.githubuser.presentation.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thatnawfal.githubuser.data.model.response.DetailUsersModel
import com.thatnawfal.githubuser.data.model.response.SearchResponse
import com.thatnawfal.githubuser.data.model.response.UsersModel
import com.thatnawfal.githubuser.data.network.service.ApiClient
import com.thatnawfal.githubuser.wrapper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _defaultList = MutableLiveData<List<UsersModel>>()
    val defaultList: LiveData<List<UsersModel>> = _defaultList

    private val _listUsers = MutableLiveData<List<UsersModel>>()
    val listUsers: LiveData<List<UsersModel>> = _listUsers

    private val _listFollowers = MutableLiveData<List<UsersModel>>()
    val listFollowers: LiveData<List<UsersModel>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<UsersModel>>()
    val listFollowing: LiveData<List<UsersModel>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<DetailUsersModel>()
    val user: LiveData<DetailUsersModel> = _user

    private val _snackbarMsg = MutableLiveData<Event<String>>()
    val snackbarMsg: LiveData<Event<String>> = _snackbarMsg

    init {
        loadUsers()
    }

    fun loadUsers(per_page : Int = 10) {
        _isLoading.value = true
        val client = ApiClient.instances().getUsers(per_page)

        client.enqueue(object : Callback<List<UsersModel>> {
            override fun onResponse(
                call: Call<List<UsersModel>>,
                response: Response<List<UsersModel>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _defaultList.value = response.body() as List<UsersModel>
                }
            }

            override fun onFailure(call: Call<List<UsersModel>>, t: Throwable) {
                _isLoading.value = false
                _snackbarMsg.value = Event(t.message.toString())
            }

        })
    }

    fun searchUsers(username: String, per_page: Int = 0) {
        _isLoading.value = true
        val client = ApiClient.instances().searchUsers(username, per_page)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()?.items as List<UsersModel>
                } else {
                    _snackbarMsg.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarMsg.value = Event(t.message.toString())
            }

        })
    }

    fun detailUser(username: String) {
        _isLoading.value = true
        val client = ApiClient.instances().getDetailUser(username)

        client.enqueue(object : Callback<DetailUsersModel> {
            override fun onResponse(
                call: Call<DetailUsersModel>,
                response: Response<DetailUsersModel>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _snackbarMsg.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<DetailUsersModel>, t: Throwable) {
                _isLoading.value = false
                _snackbarMsg.value = Event(t.message.toString())
            }

        })
    }

    fun getListFollows(username: String, follows: String, per_page: Int = 0) {
        _isLoading.value = true
        val client = ApiClient.instances().getFollowsList(username, follows, per_page)

        client.enqueue(object : Callback<List<UsersModel>> {
            override fun onResponse(
                call: Call<List<UsersModel>>,
                response: Response<List<UsersModel>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    when (follows) {
                        "followers" -> _listFollowers.value = response.body() as List<UsersModel>
                        "following" -> _listFollowing.value = response.body() as List<UsersModel>
                    }
                } else {
                    _snackbarMsg.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<UsersModel>>, t: Throwable) {
                _isLoading.value = false
                _snackbarMsg.value = Event(t.message.toString())
            }

        })
    }

    fun emptySearchField() {
        _defaultList.value = defaultList.value
    }
}