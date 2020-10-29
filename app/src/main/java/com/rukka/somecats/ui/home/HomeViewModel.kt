package com.rukka.somecats.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rukka.somecats.network.Api
import com.rukka.somecats.network.entities.NewCats
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var _newCatsList = MutableLiveData<List<NewCats>>()
    val newCatsList: LiveData<List<NewCats>>
        get() = _newCatsList

    init {
        getNewCatsData()
    }

    private fun getNewCatsData() {
        viewModelScope.launch {
            try {
                _newCatsList.value = Api.retrofitService.getNewCats()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}