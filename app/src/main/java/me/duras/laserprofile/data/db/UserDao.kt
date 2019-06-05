package me.duras.laserprofile.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun get(): User

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun updateTodo(user: User)
}