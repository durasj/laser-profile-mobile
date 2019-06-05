package me.duras.laserprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game_detail.*
import kotlinx.android.synthetic.main.profile_fragment.*

class GameDetailActivity : AppCompatActivity() {

    private var viewModel: GameDetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        val gameId = intent.extras.getInt("game")

        viewModel = ViewModelProviders.of(this).get(GameDetailViewModel::class.java)
        viewModel!!.getGame(gameId).observe(this, Observer { game ->
            // TODO: Player list - we currently don't have it from the API
            /*if (game !== null) {
                val adapter = GamesListAdapter(this, game)
                recentGamesList.setAdapter(adapter)
            }*/

            gameTitle.text = "${game?.players} players ${game?.teams}"
            timeAgoDetail.text = game?.ago
            detailInfo.text = "ID: ${game?.id}\nPlayers: ${game?.players}\nTeams: ${game?.teams}\nMode: ${game?.mode}\nCustom settings: ${game?.settings ?: "N/A"}\nYour points: ${game?.points}p"
        })
    }

    fun onBackButtonClick(view: View) {
        finish()
    }
}
