package me.duras.laserprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.duras.laserprofile.ui.login.LoginActivity
import android.content.Intent



class InitialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIntent: Intent

        // go straight to main if a token is stored
        if (true === false) {
            activityIntent = Intent(this, MainActivity::class.java)
        } else {
            activityIntent = Intent(this, LoginActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }
}
