package edu.bu.homies.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Home(@PrimaryKey(autoGenerate = true)
                   val id: Int,
                var title: String,
                var description: String,
                var links: Array<String>,
                var keywords: Array<String>,
                var isFavorite: Boolean){
//    companion object {
//        val projects = mutableListOf(
//            Home(0, "Home Portal", "Home portal is a simple Android application to provide a centralized portal for all projects.", "Andrew Ouellette, Steve Chin", arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true),
//            Home(1, "Home Test", "Home test is a simple Android application to test projects.", "Sir. Ouellette", arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true),
//            Home(2, "Another Home", "Home test is a simple Android application to test projects.", "Dr. Ouellette", arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true),
//            Home(3, "Last Home", "Last project is the last test project.", "Mr. Ouellette", arrayOf("github.com","bu.edu"), arrayOf("Class","Instructors","CS683"), true)
//        )
//    }
}