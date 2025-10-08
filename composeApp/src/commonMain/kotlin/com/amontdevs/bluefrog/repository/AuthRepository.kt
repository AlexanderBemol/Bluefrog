package com.amontdevs.bluefrog.repository

import com.amontdevs.bluefrog.domain.BlueFrogResult
import com.amontdevs.bluefrog.domain.NetworkNotAvailableException
import com.amontdevs.bluefrog.source.remote.IAuthManager
import com.amontdevs.bluefrog.util.IBluefrogLogger
import com.amontdevs.bluefrog.utils.NetworkConnectivityHelper
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface IAuthRepository {
    val sessionStatus: StateFlow<SessionStatus>

    fun getCurrentUser(): BlueFrogResult<UserInfo>

    suspend fun retrieveUserForCurrentSession(): BlueFrogResult<UserInfo>

    suspend fun signUp(
        email: String,
        password: String,
    ): BlueFrogResult<UserInfo>

    suspend fun login(
        email: String,
        password: String,
    ): BlueFrogResult<UserInfo>

    suspend fun resetPassword(email: String): BlueFrogResult<Unit>

    suspend fun signOut(): BlueFrogResult<Unit>
}

class AuthRepository(
    private val logger: IBluefrogLogger,
    private val authManager: IAuthManager,
    private val networkConnectivityHelper: NetworkConnectivityHelper,
) : IAuthRepository {
    override val sessionStatus = authManager.sessionStatus

    override fun getCurrentUser(): BlueFrogResult<UserInfo> =
        try {
            val userInfo = authManager.currentUser()
            if (userInfo != null) {
                BlueFrogResult.Success(userInfo)
            } else {
                BlueFrogResult.Error(Exception("No user found"))
            }
        } catch (e: Exception) {
            logger.e(e.message.toString(), TAG)
            BlueFrogResult.Error(e)
        }

    override suspend fun retrieveUserForCurrentSession(): BlueFrogResult<UserInfo> {
        return if (networkConnectivityHelper.isNetworkAvailable()) {
            try {
                val userInfo = authManager.retrieveUserForCurrentSession()
                logger.d("Retrieve user for current session: $userInfo", TAG)
                BlueFrogResult.Success(userInfo)
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

    override suspend fun resetPassword(email: String): BlueFrogResult<Unit> =
        try {
            authManager.resetPassword(email)
            BlueFrogResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e.message.toString(), TAG)
            BlueFrogResult.Error(e)
        }

    companion object {
        const val TAG = "AuthRepository"
    }
}
