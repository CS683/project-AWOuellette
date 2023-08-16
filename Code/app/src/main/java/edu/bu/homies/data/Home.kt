package edu.bu.homies.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homes")
data class Home(@PrimaryKey(autoGenerate = true)
                   val id: Int,
                var title: String,
                var description: String,
                var links: Array<String>,
                var keywords: Array<String>,
                var reminders: Array<String>,
                var isFavorite: Boolean){
}