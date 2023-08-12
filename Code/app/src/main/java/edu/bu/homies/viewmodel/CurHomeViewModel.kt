package edu.bu.homies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.bu.homies.HomiesApplication
import edu.bu.homies.data.Home

class CurHomeViewModel(application: Application): AndroidViewModel(application){
    private val _curHome: MutableLiveData<Home> = MutableLiveData()
    val curHome: LiveData<Home>
        get() = _curHome

    val projectPortalRepository = (application as HomiesApplication).homiesRepository

    fun initCurProject(home: Home){
        if(_curHome.value == null)
            _curHome.value = home
//        _curHome.value?.let {
//            Home.projects[0]
//        }
    }

    fun setCurProject(home: Home){
        _curHome.value = home
    }

    fun updateCurProject(title: String, desp: String, authors: String, links: Array<String>, keywords: Array<String>){
        _curHome.value?.apply{
            this.title = title
            this.description = desp
            this.authors = authors
            this.links = links
            this.keywords = keywords
        }
        projectPortalRepository.editProject(_curHome.value!!)
    }

    fun updateProjectSwitch(isFav: Boolean){
        _curHome.value?.apply {
            this.isFavorite = isFav
        }
        projectPortalRepository.editProject(_curHome.value!!)
    }

    fun updateProjectLinks(links: Array<String>){
        _curHome.value?.apply {
            this.links = links
        }
        projectPortalRepository.editProject(_curHome.value!!)
    }

    fun isCurProject(home:Home):Boolean{
        return _curHome.value?.id == home.id
    }
}