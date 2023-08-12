package edu.bu.homies.data

import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class HomiesRepository (
    private val homeDao: HomeDao
) {
    val executor =  Executors.newSingleThreadExecutor()

    fun addProject(home: Home){
        executor.execute {
            homeDao.addProject(home)
        }
    }

    fun delProject(home: Home) {
        executor.execute {
            homeDao.delProject(home)
        }
    }

    fun editProject(home: Home) {
        executor.execute {
            homeDao.editProject(home)
        }
    }

    fun getAllProjects(): LiveData<List<Home>> {
        return homeDao.getAllProjects()
    }

    fun searchProject(projId: Long): LiveData<Home> {
        return homeDao.searchProjectById(projId)
    }

    fun searchProjectsbyTitle(projTitle:String): LiveData<List<Home>> {
        return homeDao.searchProjectsByTitle(projTitle)
    }

    fun count(): LiveData<Int> {
        return homeDao.count()
    }
}