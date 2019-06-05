package me.duras.laserprofile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_ranking_list.*
import me.duras.laserprofile.data.db.Game

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [RankingsFragment.OnListFragmentInteractionListener] interface.
 */
class RankingsFragment : Fragment() {

    private var viewModel: RankingsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ranking_list, container, false)

        viewModel = ViewModelProviders.of(this).get(RankingsViewModel::class.java)
        viewModel!!.getWeeklyGames().observe(this, Observer { games ->
            if (games !== null) {
                (rankingsList.adapter as RankingRecyclerViewAdapter).setData(games)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragmentContext = context

        // Set the adapter
        with(rankingsList) {
            layoutManager = LinearLayoutManager(fragmentContext)
            adapter = RankingRecyclerViewAdapter(viewModel!!.getWeeklyGames().value ?: ArrayList<Game>())
        }

        rankingTabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position == 0) {
                    viewModel!!.getWeeklyGames()
                }
                if(tab.position == 1) {
                    viewModel!!.getMonthlyGames()
                }
                if(tab.position == 2) {
                    viewModel!!.getYearlyGames()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            RankingsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
