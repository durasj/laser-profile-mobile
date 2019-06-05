package me.duras.laserprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.duras.laserprofile.ui.login.LoginActivity
import android.content.Intent
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import me.duras.laserprofile.data.db.User


// Inspired by the blogpost from Pierce Zaifman
// https://android.jlelse.eu/login-and-main-activity-flow-a52b930f8351
class InitialActivity : AppCompatActivity() {

    private val userData: MutableLiveData<User?> = MutableLiveData()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GetUserTask(userData).execute()

        userData.observe(this, Observer { user ->
            val activityIntent = if (user === null) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }

            startActivity(activityIntent)
            finish()
        })
    }
}
