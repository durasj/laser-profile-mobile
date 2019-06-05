package me.duras.laserprofile

import android.os.AsyncTask
import android.text.format.DateUtils
import androidx.lifecycle.*
import me.duras.laserprofile.data.db.Game
import me.duras.laserprofile.data.db.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GamesViewModel() : ViewModel() {
    private val user: MutableLiveData<User?> = MutableLiveData()
    private val games: MutableLiveData<List<Game>?> = MutableLiveData()

    class GetUserTask(
        private var userData: MutableLiveData<User?>
    ) : AsyncTask<Unit, Unit, User>() {
        override fun doInBackground(vararg params: Unit?): User? {
            return LaserProfileApplication.db?.userDao()?.get()
        }

        override fun onPostExecute(result: User?) {
            userData.postValue(result)
        }
    }

    class GetGamesTask(
        private var gamesData: MutableLiveData<List<Game>?>,
        private var userData: MutableLiveData<User?>
    ) : AsyncTask<Unit, Unit, List<Game>>() {
        override fun doInBackground(vararg params: Unit?): List<Game>? {
            return LaserProfileApplication.db?.gameDao()?.getAll()
        }

        override fun onPostExecute(result: List<Game>?) {
            gamesData.value = result

            // Fetch newest list
            FetchLatestGamesTask(gamesData, userData).execute()
        }
    }

    class FetchLatestGamesTask(
        private var gamesData: MutableLiveData<List<Game>?>,
        private var userData: MutableLiveData<User?>
    ) : AsyncTask<Unit, Unit, List<Game>?>() {
        override fun doInBackground(vararg params: Unit?): List<Game>? {
            var token = userData.value?.accessToken
            val refreshToken = userData.value?.refreshToken
            if (token === null) {
                return null
            }

            var response = LaserProfileApplication.gamesService?.all("Bearer $token")?.execute()

            if (response?.code() === 401) {
                // Refresh token
                val request = LaserProfileApplication.authService?.token("Bearer $refreshToken")
                val tokenResponse = request?.execute()

                if (tokenResponse?.code() !== 200 || tokenResponse?.body() === null) {
                    return null
                }

                val token = tokenResponse?.body()!!.token
                response = LaserProfileApplication.gamesService?.all("Bearer $token")?.execute()
            } else if (response?.code() !== 200) {
                return null
            }

            val fetchedGames = response?.body()!!

            val newGames = ArrayList<Game>()
            fetchedGames.forEach { model ->
                // Inspiration from answer by Meno Hochschild
                // https://stackoverflow.com/questions/35858608/how-to-convert-time-to-time-ago-in-android/35860996#35860996
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
                val time = sdf.parse(model.played).getTime()
                val now = System.currentTimeMillis()
                val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

                newGames.add(Game(
                    id = model.id,
                    played = model.played,
                    ago = ago.toString(),
                    mode = model.mode,
                    players = model.players,
                    teams = model.teams,
                    settings = model.settings,
                    attachment = model.attachment,
                    points = model.points
                ))
            }

            LaserProfileApplication.db?.gameDao()?.clear()
            LaserProfileApplication.db?.gameDao()?.insertAll(*newGames.toTypedArray())
            return newGames
        }

        override fun onPostExecute(result: List<Game>?) {
            if (result !== null) {
                gamesData.value = result
            }
        }
    }

    fun getGames(): LiveData<List<Game>?> {
        GetUserTask(user).execute()
        GetGamesTask(games, user).execute()

        return games
    }

}
