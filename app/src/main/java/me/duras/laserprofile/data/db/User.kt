package me.duras.laserprofile.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey var id: Int,
    var nick: String,
    var email: String,
    var role: String,
    var refreshToken: String,
    var accessToken: String
)
