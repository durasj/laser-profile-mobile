package me.duras.laserprofile

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.duras.laserprofile.GamesFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.game_list_view.view.*
import me.duras.laserprofile.data.db.Game

/**
 * [RecyclerView.Adapter] that can display a [Games] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class RankingRecyclerViewAdapter(
    private var games: List<Game>
) : RecyclerView.Adapter<RankingRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = games[position]

        with(holder.view) {
            holder.game = item
        }
    }

    override fun getItemCount(): Int = games.size

    fun setData(newData: List<Game>) {
        this.games = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var game: Game? = null
            set(value) {
                field = value
                view.gameId.text = value?.id.toString()
                view.name.text = "${value?.players} players ${value?.teams}"
                view.timeAgo.text = value?.ago
                view.points.text = "${value?.points}p"
            }
    }
}
