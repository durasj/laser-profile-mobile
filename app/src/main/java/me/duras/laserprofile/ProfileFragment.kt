package me.duras.laserprofile

import android.content.Intent
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*
import java.math.BigInteger
import java.security.MessageDigest
import androidx.navigation.fragment.findNavController
import me.duras.laserprofile.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    class ClearDatabaseTask() : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            LaserProfileApplication.db?.clearAllTables()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        allGamesButton.setOnClickListener {
            findNavController().navigate(R.id.gamesFragment)
        }

        logout.setOnClickListener {
            ClearDatabaseTask().execute()

            val activityIntent = Intent(activity, LoginActivity::class.java)
            startActivity(activityIntent)

            activity!!.finish()
        }

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getUser().observe(this, Observer { user ->
            if (user !== null) {
                welcomeText.text = "What's up, ${user.nick}!"

                val hash = getMd5(user.email)

                Picasso.get()
                    .load("http://www.gravatar.com/avatar/$hash?s=512&d=404")
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(profileImage)
            }
        })
        viewModel.getGames().observe(this, Observer { games ->
            if (games !== null) {
                val adapter = GamesListAdapter(activity!!, games)
                recentGamesList.setAdapter(adapter)
            }
        })
    }

    private fun getMd5(input: String): String? {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest()).toString(16).padStart(32, '0')
    }
}
