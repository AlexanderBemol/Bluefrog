package com.amontdevs.bluefrog.util

interface IAuthFlowHelper {
    suspend fun startGoogleHelper(): AuthFlowResult
    suspend fun startFacebookHelper(): AuthFlowResult
}

data class AuthFlowResult (
    val idToken: String,
    val nonce: String
)