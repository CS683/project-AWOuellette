package edu.bu.homies.data

import androidx.lifecycle.LiveData
import androidx.room.*
/*
This HomeDao interface defines various CRUD operations
to access the homes table in the database
 */
@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHome(home: Home):Long

    @Delete
    fun delHome(home: Home)

    @Update
    fun editHome(home: Home)

    @Query("SELECT count(*) From homes")
    fun count(): LiveData<Int>

    @Query("SELECT * FROM homes")
    fun getAllHomes(): LiveData<List<Home>>

    @Query("SELECT * FROM homes where id = :homeId")
    fun searchHomeByID(homeId: Long): LiveData<Home>

    @Query("SELECT * FROM homes WHERE title like :homeTitle ")
    fun searchHomesByTitle(homeTitle:String): LiveData<List<Home>>
}