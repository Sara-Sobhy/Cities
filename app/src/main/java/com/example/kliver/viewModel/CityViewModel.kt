package com.example.kliver.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kliver.model.City
import com.example.kliver.network.CityRepository
import kotlinx.coroutines.launch

class CityViewModel(private val repository: CityRepository) : ViewModel() {

    private val _filteredCities = MutableLiveData<List<City>>()
    val filteredCities: LiveData<List<City>> get() = _filteredCities

    fun filterCities(prefix: String) {
        viewModelScope.launch {
            val filteredList = repository.getCitiesByPrefix(prefix)
            _filteredCities.postValue(filteredList)
        }
    }
}

class CityViewModelFactory(private val repository: CityRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityViewModel::class.java)) {
            return CityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
