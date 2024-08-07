package com.example.kliver.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kliver.R
import com.example.kliver.model.City
import com.example.kliver.network.CityRepository
import com.example.kliver.viewModel.CityViewModel
import com.example.kliver.viewModel.CityViewModelFactory
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CityViewModel
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cities = loadCitiesFromAsset()
        val repository = CityRepository(cities)
        viewModel = ViewModelProvider(this, CityViewModelFactory(repository)).get(CityViewModel::class.java)
        adapter = CityAdapter()

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val searchBar: EditText = findViewById(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.filterCities(s.toString())
            }
        })

        viewModel.filteredCities.observe(this, { cities ->
            adapter.setCities(cities)
        })
    }

    private fun loadCitiesFromAsset(): List<City> {
        val json = assets.open("cities.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(json, Array<City>::class.java).toList()
    }

}