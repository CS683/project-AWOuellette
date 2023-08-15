package edu.bu.homies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import edu.bu.homies.HomiesApplication
import edu.bu.homies.data.Home
import java.util.concurrent.Executors

class HomeListViewModel(application: Application): AndroidViewModel(application) {

    val homeRepository = (application as HomiesApplication).homiesRepository

    private val _homeList: LiveData<List<Home>> = homeRepository.getAllHomes()
    val homeList: LiveData<List<Home>>
        get() = _homeList

    fun getAllHomes(): LiveData<List<Home>> {
        return homeRepository.getAllHomes()
    }

    fun addHome(home: Home){
        Executors.newSingleThreadExecutor().execute {
            homeRepository.addHome(home)
        }
    }

    fun count(): Int{
        return homeRepository.count().value?:0
    }

    fun delHome(home: Home) {
        homeRepository.delHome(home)
    }

}