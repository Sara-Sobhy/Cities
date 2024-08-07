package com.example.kliver.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kliver.R
import com.example.kliver.model.City

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private var cities: List<City> = listOf()

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.bind(city)
    }

    override fun getItemCount() = cities.size

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.city_title)
        private val subtitle: TextView = itemView.findViewById(R.id.city_subtitle)

        fun bind(city: City) {
            title.text = "${city.name}, ${city.country}"
            subtitle.text = "Coordinates: ${city.coord.lat}, ${city.coord.lon}"
            itemView.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${city.coord.lat},${city.coord.lon}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                it.context.startActivity(mapIntent)
            }
        }
    }
}
