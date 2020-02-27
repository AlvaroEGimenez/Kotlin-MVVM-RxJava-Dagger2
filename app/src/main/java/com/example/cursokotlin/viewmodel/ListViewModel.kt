package com.example.cursokotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cursokotlin.di.DaggerApiComponent
import com.example.cursokotlin.model.CountriesService
import com.example.cursokotlin.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {

    @Inject
    lateinit var countryService : CountriesService

    init {
        DaggerApiComponent.create().inject(this)
    }
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun refresh() {
        fetchCountries()
    }


    private fun fetchCountries() {
        loading.value = true

        disposable.add(countryService.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(value: List<Country>?) {
                    countries.value = value
                    countryLoadError.value = false
                    loading.value = false
                }

                override fun onError(e: Throwable?) {
                    countryLoadError.value = true
                    loading.value = false
                }

            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}