package com.example.cursokotlin.model

import com.example.cursokotlin.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountriesService {

    @Inject
    lateinit var  api: CountriesApi


    init {
    DaggerApiComponent.create().inject(this)
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }
}