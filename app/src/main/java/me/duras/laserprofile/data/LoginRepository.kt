package me.duras.laserprofile.data

import me.duras.laserprofile.data.model.TokenResponse
import me.duras.laserprofile.data.model.User

/**
 * Class that requests authentication and user information from the remote data source
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the user
    var user: User? = null
        private set

    // in-memory cache of the refresh token
    var refreshToken: String? = null
        private set

    // in-memory cache of the access token
    var accessToken: String? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
        refreshToken = null
        accessToken = null
    }

    fun login(email: String, password: String): Result<TokenResponse> {
        // handle login
        val result = dataSource.login(email, password)
        if (result !is Result.Success) {
            return result
        }
        setRefreshToken(result.data.token)

        val tokenResult = dataSource.getAccessToken(result.data.token);
        if (tokenResult !is Result.Success) {
            return result
        }
        setAccessToken(tokenResult.data.token)
        setLoggedInUser(tokenResult.data.user)

        return result
    }

    private fun setLoggedInUser(loggedInUser: User) {
        this.user = loggedInUser
    }

    private fun setRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    private fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }
}
