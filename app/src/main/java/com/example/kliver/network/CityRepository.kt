package com.example.kliver.network

import com.example.kliver.model.City

class CityRepository(private val cities: List<City>) {
    fun getCitiesByPrefix(prefix: String): List<City> {
        return cities.filter { it.name.startsWith(prefix, ignoreCase = true) }
            .sortedBy { it.name }
    }
}

