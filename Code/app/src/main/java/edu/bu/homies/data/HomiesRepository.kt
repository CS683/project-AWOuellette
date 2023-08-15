package edu.bu.homies.data

import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class HomiesRepository (
    private val homeDao: HomeDao
) {
    val executor =  Executors.newSingleThreadExecutor()

    fun addHome(home: Home){
        executor.execute {
            homeDao.addHome(home)
        }
    }

    fun delHome(home: Home) {
        executor.execute {
            homeDao.delHome(home)
        }
    }

    fun editHome(home: Home) {
        executor.execute {
            homeDao.editHome(home)
        }
    }

    fun getAllHomes(): LiveData<List<Home>> {
        return homeDao.getAllHomes()
    }

    fun searchHome(homeId: Long): LiveData<Home> {
        return homeDao.searchHomeByID(homeId)
    }

    fun searchHomeByTitle(homeTitle:String): LiveData<List<Home>> {
        return homeDao.searchHomesByTitle(homeTitle)
    }

    fun count(): LiveData<Int> {
        return homeDao.count()
    }
}