package edu.bu.homies

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.bu.homies.data.Home
import edu.bu.homies.data.HomiesDatabase
import edu.bu.homies.data.HomiesRepository
import java.util.concurrent.Executors

class HomiesApplication: Application() {
    lateinit var homiesDatabase: HomiesDatabase
    lateinit var homiesRepository: HomiesRepository
    override fun onCreate() {
        super.onCreate()

        homiesDatabase =
            Room.databaseBuilder(
                applicationContext, HomiesDatabase::class.java,
                "homies-db"
            ).addCallback(object : RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase){
                    super.onCreate(db)
                    addInitProjects()
                }
            })
                .build()

        homiesRepository = HomiesRepository(homiesDatabase.projectDao())
    }
    fun addInitProjects(){
        Executors.newSingleThreadScheduledExecutor().execute {
            homiesDatabase.projectDao().addProject(Home(0, "Home", "Moms birthday this week.", arrayOf("Home","Dorm","Apartment"), arrayOf("Mom","Dad","Brother"), true))
            homiesDatabase.projectDao().addProject(Home(0, "Dorm", "Text roommate about club hockey sign ups.",  arrayOf("Dorm","Apartment","Home"), arrayOf("Mankit"), false))
            homiesDatabase.projectDao().addProject(Home(0, "Apartment", "Sign lease for next year.",  arrayOf("Apartment","Home","Dorm"), arrayOf("Ivan","Charles"), true))
        }
    }
}