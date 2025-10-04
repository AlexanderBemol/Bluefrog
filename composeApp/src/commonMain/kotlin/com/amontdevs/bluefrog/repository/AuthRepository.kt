package com.amontdevs.bluefrog.repository

import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.domain.NetworkNotAvailableException
import com.amontdevs.bluefrog.source.remote.IAuthManager
import com.amontdevs.bluefrog.util.IBluefrogLogger
import com.amontdevs.bluefrog.utils.NetworkConnectivityHelper
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.user.UserInfo

interface IAuthRepository {
    suspend fun accessWithFacebook(): BlueFrogResult<Unit>

    suspend fun retrieveUserForCurrentSession(): BlueFrogResult<UserInfo>

    suspend fun signUp(
        email: String,
        password: String,
    ): BlueFrogResult<UserInfo>

    suspend fun login(
        email: String,
        password: String,
    ): BlueFrogResult<UserInfo>

    suspend fun signOut(): BlueFrogResult<Unit>
}

class AuthRepository(
    private val logger: IBluefrogLogger,
    private val authManager: IAuthManager,
    private val networkConnectivityHelper: NetworkConnectivityHelper,
) : IAuthRepository {
    override suspend fun accessWithFacebook(): BlueFrogResult<Unit> =
        try {
            authManager.accessWithFacebook()
            BlueFrogResult.Success(Unit)
        } catch (e: Exception) {
            BlueFrogResult.Error(e)
        }

    override suspend fun retrieveUserForCurrentSession(): BlueFrogResult<UserInfo> {
        return if (networkConnectivityHelper.isNetworkAvailable()) {
            try {
                if (authManager.loadSession() != null) {
                    val userInfo = authManager.retrieveUserForCurrentSession()
                    logger.d("Retrieve user for current session: $userInfo", TAG)
                    BlueFrogResult.Success(userInfo)
                } else {
                    logger.e("No session found", TAG)
                    return BlueFrogResult.Error(Exception("No session found"))
                }
            } catch (e: Exception) {
                logger.e(e.message.toString(), TAG)
                BlueFrogResult.Error(e)
            }
        } else {
            BlueFrogResult.Error(NetworkNotAvailableException())
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
    ): BlueFrogResult<UserInfo> =
        try {
            val userInfo = authManager.signUp(email, password)

            logger.d("Sign up: $userInfo", TAG)
            BlueFrogResult.Success(userInfo)
        } catch (e: Exception) {
            logger.e(e.message.toString(), TAG)
            BlueFrogResult.Error(e)
        }

    override suspend fun login(
        email: String,
        password: String,
    ): BlueFrogResult<UserInfo> =
        try {
            val userInfo = authManager.login(email, password)
            logger.d("Login: $userInfo", TAG)
            BlueFrogResult.Success(userInfo)
        } catch (e: Exception) {
            logger.e(e.message.toString(), TAG)
            BlueFrogResult.Error(e)
        }

    override suspend fun signOut(): BlueFrogResult<Unit> =
        try {
            authManager.logOut(SignOutScope.LOCAL)
            logger.d("Sign out", TAG)
            BlueFrogResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e.message.toString(), TAG)
            BlueFrogResult.Error(e)
        }

    companion object {
        const val TAG = "AuthRepository"
    }
}
