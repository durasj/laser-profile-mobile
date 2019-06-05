package me.duras.laserprofile.data

import android.util.Log
import me.duras.laserprofile.LaserProfileApplication
import me.duras.laserprofile.data.model.TokenResponse
import me.duras.laserprofile.data.model.UserLoginInfo
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    fun login(email: String, password: String): Result<TokenResponse> {
        val service = LaserProfileApplication.authService;

        try {
            val request = service?.auth(UserLoginInfo(email, password))
            val response = request?.execute()

            if (response?.code() === 401) {
                Log.e("LoginDataSource", response.errorBody()!!.string())
                return Result.Error(IOException("Wrong email or password"))
            }

            if (response?.code() !== 200 || response.body() === null) {
                Log.e("LoginDataSource", response?.errorBody()!!.string())
                return Result.Error(IOException("Error logging in"))
            }

            val tokenRequest = service?.token("Bearer " + response.body()!!.token)
            val tokenResponse = tokenRequest?.execute()

            if (tokenResponse?.code() !== 200 || tokenResponse.body() === null) {
                Log.e("LoginDataSource", response.errorBody()!!.string() ?: "")
                return Result.Error(IOException("Error logging in"))
            }

            return Result.Success(
                TokenResponse(
                    user = tokenResponse?.body()!!.user,
                    token = "${response.body()!!.token} ${tokenResponse.body()!!.token}"
                )
            )
        } catch (e: Throwable) {
            Log.e("LoginDataSource", e.message)
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun getAccessToken(refreshToken: String): Result<TokenResponse> {
        val service = LaserProfileApplication.authService;

        try {
            val request = service?.token("Bearer $refreshToken")
            val response = request?.execute();

            if (response?.code() !== 200 || response?.body() === null) {
                return Result.Error(IOException("Error renewing authentication"))
            }

            return Result.Success(response?.body()!!)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error renewing authentication", e))
        }
    }
}
