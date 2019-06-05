package me.duras.laserprofile.ui.login

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import me.duras.laserprofile.data.LoginRepository
import me.duras.laserprofile.data.Result

import me.duras.laserprofile.R

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    class LoginTask(
        private val loginResult: MutableLiveData<LoginResult>,
        private val loginRepository: LoginRepository,
        private val username: String,
        private val password: String
    ) : AsyncTask<Unit, LoginResult, LoginResult>() {
        override fun doInBackground(vararg params: Unit?): LoginResult {
            val result = loginRepository.login(username, password)

            return if (result is Result.Success) {
                LoginResult(success = result.data)
            } else {
                LoginResult(error = result.toString())
            }
        }

        override fun onPostExecute(result: LoginResult?) {
            loginResult.value = result
        }
    }

    fun login(username: String, password: String) {
        LoginTask(_loginResult, loginRepository, username, password).execute()
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return (!username.isBlank() && Patterns.EMAIL_ADDRESS.matcher(username).matches())
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
