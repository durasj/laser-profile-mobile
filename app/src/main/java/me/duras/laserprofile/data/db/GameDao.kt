package me.duras.laserprofile.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE id = :id")
    fun getById(id: Int): Game

    @Query("SELECT * FROM games")
    fun getAll(): List<Game>

    @Query("SELECT * FROM games LIMIT 3")
    fun getLatest(): List<Game>

    @Query("SELECT * FROM games WHERE DATE(played) > DATE('now', '-1 week') ORDER BY points DESC")
    fun getBestWeek(): List<Game>

    @Query("SELECT * FROM games WHERE DATE(played) > DATE('now', '-1 month') ORDER BY points DESC")
    fun getBestMonth(): List<Game>

    @Query("SELECT * FROM games WHERE DATE(played) > DATE('now', '-1 year') ORDER BY points DESC")
    fun getBestYear(): List<Game>

    @Insert
    fun insertAll(vararg game: Game)

    @Query("DELETE FROM games")
    fun clear()
}
