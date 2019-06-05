package me.duras.laserprofile.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    var id: Int,
    var played: String,
    var ago: String,
    var mode: String,
    var players: Int,
    var teams: String,
    var settings: String?,
    var attachment: String?,
    var points: Int
)
