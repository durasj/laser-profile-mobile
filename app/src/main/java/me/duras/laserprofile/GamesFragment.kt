package me.duras.laserprofile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.duras.laserprofile.data.db.Game

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [GamesFragment.OnListFragmentInteractionListener] interface.
 */
class GamesFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    private var viewModel: GamesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)

        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        viewModel!!.getGames().observe(this, Observer { games ->
            if (games !== null && view is RecyclerView && view.adapter is GameRecyclerViewAdapter) {
                (view.adapter as GameRecyclerViewAdapter).setData(games)
            }
        })

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = GameRecyclerViewAdapter(viewModel!!.getGames().value ?: ArrayList<Game>(), listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(gameId: Int?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            GamesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
