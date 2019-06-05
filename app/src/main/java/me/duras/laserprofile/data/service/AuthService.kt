package me.duras.laserprofile.data.service

import me.duras.laserprofile.data.model.TokenResponse
import me.duras.laserprofile.data.model.UserLoginInfo

import retrofit2.Call
import retrofit2.http.*

interface AuthService {
    @POST("auth")
    fun auth(@Body user: UserLoginInfo): Call<TokenResponse>

    @GET("token")
    fun token(@Header("Authorization") authorization: String): Call<TokenResponse>
}
