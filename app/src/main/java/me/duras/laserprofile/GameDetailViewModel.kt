package me.duras.laserprofile

import android.os.AsyncTask
import android.text.format.DateUtils
import androidx.lifecycle.*
import me.duras.laserprofile.data.db.Game
import me.duras.laserprofile.data.db.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GameDetailViewModel() : ViewModel() {
    private val game: MutableLiveData<Game?> = MutableLiveData()

    class GetGameTask(
        private var gameData: MutableLiveData<Game?>,
        private var id: Int
    ) : AsyncTask<Unit, Unit, Game>() {
        override fun doInBackground(vararg params: Unit?): Game? {
            return LaserProfileApplication.db?.gameDao()?.getById(id)
        }

        override fun onPostExecute(result: Game?) {
            gameData.value = result
        }
    }

    fun getGame(id: Int): LiveData<Game?> {
        GetGameTask(game, id).execute()

        return game
    }

}
