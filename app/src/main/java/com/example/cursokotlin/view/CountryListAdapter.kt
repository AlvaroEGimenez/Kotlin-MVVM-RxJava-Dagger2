package com.example.cursokotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cursokotlin.R
import com.example.cursokotlin.model.Country
import com.example.cursokotlin.util.getProgressDrawable
import com.example.cursokotlin.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )

    override fun getItemCount() = countries.size


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bing(countries[position])
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryName = view.name
        private val imageview = view.imageView
        private val countryCapital = view.capital
        private val progressDrawable = getProgressDrawable(view.context)

        fun bing(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital
            imageview.loadImage(country.flag, progressDrawable)
        }
    }

}