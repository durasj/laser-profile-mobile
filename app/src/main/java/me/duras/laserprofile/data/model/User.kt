package me.duras.laserprofile.data.model

data class User(
    val id: Int,
    val nick: String,
    val email: String,
    val role: String,
    val created_at: String,
    val updated_at: String
)
