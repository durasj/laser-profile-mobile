package me.duras.laserprofile.data.model

data class Game(
    val id: Int,
    val played: String,
    val mode: String,
    val players: Int,
    val teams: String,
    val settings: String?,
    val attachment: String?,
    val points: Int,
    val created_at: String,
    val updated_at: String
)
