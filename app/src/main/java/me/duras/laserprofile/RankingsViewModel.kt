package me.duras.laserprofile

import android.os.AsyncTask
import android.text.format.DateUtils
import androidx.lifecycle.*
import me.duras.laserprofile.data.db.Game
import me.duras.laserprofile.data.db.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RankingsViewModel() : ViewModel() {
    private val games: MutableLiveData<List<Game>?> = MutableLiveData()

    class GetGamesTask(
        private var gamesData: MutableLiveData<List<Game>?>,
        private val which: String = "week"
    ) : AsyncTask<Unit, Unit, List<Game>>() {
        override fun doInBackground(vararg params: Unit?): List<Game>? {
            if (which === "week") {
                return LaserProfileApplication.db?.gameDao()?.getBestWeek()
            } else  if (which === "month") {
                return LaserProfileApplication.db?.gameDao()?.getBestMonth()
            } else {
                return LaserProfileApplication.db?.gameDao()?.getBestYear()
            }
        }

        override fun onPostExecute(result: List<Game>?) {
            gamesData.value = result
        }
    }

    fun getWeeklyGames(): LiveData<List<Game>?> {
        GetGamesTask(games, "week").execute()

        return games
    }

    fun getMonthlyGames(): LiveData<List<Game>?> {
        GetGamesTask(games, "month").execute()

        return games
    }

    fun getYearlyGames(): LiveData<List<Game>?> {
        GetGamesTask(games, "year").execute()

        return games
    }

}
