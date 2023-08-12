package edu.bu.homies.data

import androidx.lifecycle.LiveData
import androidx.room.*
/*
This HomeDao interface defines various CRUD operations
to access the projects table in the database
 */
@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProject(home: Home):Long

    @Delete
    fun delProject(home: Home)

    @Update
    fun editProject(home: Home)

    @Query("SELECT count(*) From projects")
    fun count(): LiveData<Int>

    @Query("SELECT * FROM projects")
    fun getAllProjects(): LiveData<List<Home>>

    @Query("SELECT * FROM projects where id = :projId")
    fun searchProjectById(projId: Long): LiveData<Home>

    @Query("SELECT * FROM projects WHERE title like :projTitle ")
    fun searchProjectsByTitle(projTitle:String): LiveData<List<Home>>
}