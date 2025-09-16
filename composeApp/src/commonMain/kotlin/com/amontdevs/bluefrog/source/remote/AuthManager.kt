package com.amontdevs.bluefrog.source.remote

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.providers.Facebook
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.auth.user.UserSession

interface IAuthManager {
    suspend fun loadSession(): UserSession?

    suspend fun signUp(
        userEmail: String,
        userPassword: String,
    ): UserInfo

    suspend fun login(
        userEmail: String,
        userPassword: String,
    ): UserInfo

    suspend fun accessWithFacebook()

    suspend fun retrieveUserForCurrentSession(): UserInfo

    suspend fun resetPassword(email: String)

    suspend fun resendConfirmationMail(email: String)

    suspend fun logOut(scope: SignOutScope)
}

class AuthManager(
    private val auth: Auth,
) : IAuthManager {

    override suspend fun loadSession() =
        auth.sessionManager.loadSession()

    override suspend fun signUp(
        userEmail: String,
        userPassword: String,
    ): UserInfo {
        val userInfo = auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
        }
        return userInfo ?: auth.retrieveUserForCurrentSession()
    }

    override suspend fun login(
        userEmail: String,
        userPassword: String,
    ): UserInfo {
        auth.signInWith(Email) {
            email = userEmail
            password = userPassword
        }
        val ss = auth.sessionStatus.value
        return auth.retrieveUserForCurrentSession()
    }

    override suspend fun accessWithFacebook() {
        auth.signUpWith(Facebook){

        }
    }

    override suspend fun retrieveUserForCurrentSession() = auth.retrieveUserForCurrentSession()

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
