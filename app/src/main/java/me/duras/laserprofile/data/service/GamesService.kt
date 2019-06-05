package me.duras.laserprofile.data.service

import me.duras.laserprofile.data.model.Game

import retrofit2.Call
import retrofit2.http.*

interface GamesService {
    @GET("games")
    fun all(@Header("Authorization") authorization: String): Call<List<Game>>
}
