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
            homiesDatabase.projectDao().addProject(Home(0, "Home Portal", "Home portal is a simple Android application to provide a centralized portal for all projects.", arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true))
            homiesDatabase.projectDao().addProject(Home(0, "Home Test", "Home test is a simple Android application to test projects.",  arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true))
            homiesDatabase.projectDao().addProject(Home(0, "Another Home", "Home test is a simple Android application to test projects.",  arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true))
            homiesDatabase.projectDao().addProject(Home(0, "Last Home", "Last project is the last test project.",  arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true))
        }
    }
}