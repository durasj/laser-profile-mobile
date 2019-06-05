package me.duras.laserprofile.ui.login

import me.duras.laserprofile.data.model.TokenResponse

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: TokenResponse? = null,
    val error: String? = null
)
