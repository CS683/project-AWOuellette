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

    val homePortalRepository = (application as HomiesApplication).homiesRepository

    fun initCurHome(home: Home){
        if(_curHome.value == null)
            _curHome.value = home
    }

    fun setCurHome(home: Home){
        _curHome.value = home
    }

    fun updateCurHome(title: String, desp: String, keywords: Array<String>){
        _curHome.value?.apply{
            this.title = title
            this.description = desp
            this.keywords = keywords
        }
        homePortalRepository.editHome(_curHome.value!!)
    }

    fun updateHomeSwitch(isFav: Boolean){
        _curHome.value?.apply {
            this.isFavorite = isFav
        }
        homePortalRepository.editHome(_curHome.value!!)
    }

    fun updateHomeTypes(links: Array<String>){
        _curHome.value?.apply {
            this.links = links
        }
        homePortalRepository.editHome(_curHome.value!!)
    }

    fun isCurHome(home:Home):Boolean{
        return _curHome.value?.id == home.id
    }
}