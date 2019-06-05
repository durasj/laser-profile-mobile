package me.duras.laserprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GamesFragment.OnListFragmentInteractionListener {
    private var context: Context? = null

    override fun onListFragmentInteraction(itemId: Int?) {
        val intent = Intent(context, GameDetailActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        itemId?.let {
            val bundle = Bundle().apply { putInt("game", itemId) }
            intent.putExtras(bundle)
        }
        context?.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = applicationContext

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(navigation, navController)
    }
}
