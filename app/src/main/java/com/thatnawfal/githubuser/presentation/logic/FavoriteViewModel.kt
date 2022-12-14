package com.thatnawfal.githubuser.presentation.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatnawfal.githubuser.data.local.database.entity.FavoriteEntity
import com.thatnawfal.githubuser.data.repository.FavoriteRepository
import com.thatnawfal.githubuser.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val mFavRepository: FavoriteRepository ): ViewModel() {

    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited : LiveData<Boolean> = _isFavorited

    private val _snackbarMsg = MutableLiveData<Event<String>>()
    val snackbarMsg: LiveData<Event<String>> = _snackbarMsg

    fun addFavorite(favorite: FavoriteEntity) {
        mFavRepository.addFavorite(favorite)
        _snackbarMsg.value = Event("Added To Favorite")
    }

    fun removeFavorite(favorite: FavoriteEntity){
        mFavRepository.removeFavorite(favorite)
        _snackbarMsg.value = Event("Removed From Favorite")
    }

    fun checkFavorite(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val responsed = mFavRepository.checkFavorite(id)
            viewModelScope.launch(Dispatchers.Main) {
                _isFavorited.postValue(responsed)
            }
        }
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavRepository.getAllFavorites()
    fun getSomeFavorites(): LiveData<List<FavoriteEntity>> = mFavRepository.getSomeFavorite()
}