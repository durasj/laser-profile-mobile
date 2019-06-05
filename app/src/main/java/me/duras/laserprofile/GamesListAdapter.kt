package me.duras.laserprofile

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import me.duras.laserprofile.data.db.Game

class GamesListAdapter(private val context: FragmentActivity, private val games: List<Game>): BaseAdapter() {
    override fun getCount(): Int {
        return games.size
    }

    override fun getItemId(p0: Int): Long {
        return games.get(p0).id.toLong()
    }

    override fun getItem(p0: Int): Game {
        return games.get(p0)
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val game = games.get(p0)

        val inflater = context.getLayoutInflater()
        val rowView = inflater.inflate(R.layout.game_list_view, null, true)

        val nameText = rowView.findViewById(R.id.name) as TextView
        val timeAgoText = rowView.findViewById(R.id.timeAgo) as TextView
        val pointsText = rowView.findViewById(R.id.points) as TextView

        nameText.text = "${game.players} players ${game.teams}"
        timeAgoText.text = game.ago
        pointsText.text = "${game?.points}p"

        return rowView
    }

}