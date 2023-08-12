package edu.bu.homies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import edu.bu.homies.HomiesApplication
import edu.bu.homies.data.Home
import java.util.concurrent.Executors

class HomeListViewModel(application: Application): AndroidViewModel(application) {

//    val homiesDatabase = (application as HomiesApplication).homiesDatabase
//    val projectDao = homiesDatabase.projectDao()

    val projectPortalRepository = (application as HomiesApplication).homiesRepository

    private val _homeList: LiveData<List<Home>> = projectPortalRepository.getAllProjects()
    val homeList: LiveData<List<Home>>
        get() = _homeList

    fun getAllProjects(): LiveData<List<Home>> {
        return projectPortalRepository.getAllProjects()
    }

    fun addProject(home: Home){
        Executors.newSingleThreadExecutor().execute {
            projectPortalRepository.addProject(home)
        }
    }

    fun count(): Int{
        return projectPortalRepository.count().value?:0
    }

    fun delProject(home: Home) {
        projectPortalRepository.delProject(home)
    }

//    init{
//        _homeList.value = Home.projects
//    }
}