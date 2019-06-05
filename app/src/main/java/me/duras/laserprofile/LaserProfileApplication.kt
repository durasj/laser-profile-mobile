package me.duras.laserprofile

import android.app.Application
import android.content.Context
import androidx.room.Room
import me.duras.laserprofile.data.db.AppDatabase
import me.duras.laserprofile.data.service.AuthService
import me.duras.laserprofile.data.service.GamesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LaserProfileApplication : Application() {
    companion object {
        const val API_URL = "https://laserprofile.app/api/"
        var db: AppDatabase? = null
        var api: Retrofit? = null

        var authService: AuthService? = null
        var gamesService: GamesService? = null
    }

    override fun onCreate() {
        db =
            Room.databaseBuilder(
                applicationContext as Context,
                AppDatabase::class.java, "laser.db"
            ).build()

        api = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = LaserProfileApplication.api?.create(AuthService::class.java)
        gamesService = LaserProfileApplication.api?.create(GamesService::class.java)

        super.onCreate()
    }

}
