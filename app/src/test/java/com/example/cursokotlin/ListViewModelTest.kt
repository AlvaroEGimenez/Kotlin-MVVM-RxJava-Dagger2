package com.example.cursokotlin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cursokotlin.model.CountriesService
import com.example.cursokotlin.model.Country
import com.example.cursokotlin.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()


    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSucess() {
        val country = Country("cuontryName", "capital", "url")
        val countruList = arrayListOf(country)

        testSingle = Single.just(countruList)

        `when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.countries.value?.size)

        Assert.assertEquals(false, listViewModel.countryLoadError.value)

        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getCountriesFail (){
        testSingle = Single.error(Throwable())

        `when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(true,listViewModel.countryLoadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)
    }


    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker { it.run() }
            }
        }


        RxJavaPlugins.setInitIoSchedulerHandler { sheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

}