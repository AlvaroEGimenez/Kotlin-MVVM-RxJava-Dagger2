package com.example.cursokotlin.di

import com.example.cursokotlin.model.CountriesService
import com.example.cursokotlin.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountriesService)

    fun inject(viewModel: ListViewModel)
}