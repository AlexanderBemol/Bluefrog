package com.amontdevs.bluefrog.source.remote

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.providers.Facebook
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.coroutines.flow.StateFlow

interface IAuthManager {
    val sessionStatus: StateFlow<SessionStatus>

    suspend fun loadSession(): UserSession?

    suspend fun signUp(
        userEmail: String,
        userPassword: String,
    ): UserInfo

    suspend fun login(
        userEmail: String,
        userPassword: String,
    ): UserInfo

    suspend fun retrieveUserForCurrentSession(): UserInfo

    fun currentUser(): UserInfo?

    suspend fun resetPassword(email: String)

    suspend fun resendConfirmationMail(email: String)

    suspend fun logOut(scope: SignOutScope)
}

class AuthManager(
    private val auth: Auth,
) : IAuthManager {
    override val sessionStatus = auth.sessionStatus

    override suspend fun loadSession() = auth.sessionManager.loadSession()

    override suspend fun signUp(
        userEmail: String,
        userPassword: String,
    ): UserInfo {
        val userInfo =
            auth.signUpWith(Email) {
                email = userEmail
                password = userPassword
            }
        return userInfo ?: retrieveUserForCurrentSession()
    }

    override suspend fun login(
        userEmail: String,
        userPassword: String,
    ): UserInfo {
        auth.signInWith(Email) {
            email = userEmail
            password = userPassword
        }
        return auth.retrieveUserForCurrentSession(true)
    }

    override suspend fun retrieveUserForCurrentSession() = auth.retrieveUserForCurrentSession(true)

    override fun currentUser() = auth.currentUserOrNull()

    override suspend fun resetPassword(email: String) {
        auth.resetPasswordForEmail(email)
    }

    override suspend fun resendConfirmationMail(email: String) {
        auth.resendEmail(OtpType.Email.EMAIL, email)
    }

    override suspend fun logOut(scope: SignOutScope) {
        auth.signOut(scope)
    }
}
