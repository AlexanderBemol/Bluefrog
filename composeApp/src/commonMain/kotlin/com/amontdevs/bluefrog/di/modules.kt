package com.amontdevs.bluefrog.di

import com.amontdevs.bluefrog.repository.AbsoluteSessionRepository
import com.amontdevs.bluefrog.repository.AudioRepository
import com.amontdevs.bluefrog.repository.AuthRepository
import com.amontdevs.bluefrog.repository.IAbsoluteSessionRepository
import com.amontdevs.bluefrog.repository.IAudioRepository
import com.amontdevs.bluefrog.repository.IAuthRepository
import com.amontdevs.bluefrog.source.local.INotesPlayer
import com.amontdevs.bluefrog.source.remote.AuthManager
import com.amontdevs.bluefrog.source.remote.IAuthManager
import com.amontdevs.bluefrog.util.IBluefrogLogger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import org.koin.dsl.module

private fun buildAudioRepository(notesPlayer: INotesPlayer): IAudioRepository = AudioRepository(notesPlayer)

private fun buildAbsoluteSessionRepository(sessionId: Int): IAbsoluteSessionRepository = AbsoluteSessionRepository(sessionId)

private fun buildAuthRepository(
    logger: IBluefrogLogger,
    authManager: IAuthManager,
): IAuthRepository = AuthRepository(logger, authManager)

val repositoryModule =
    module {
        factory { buildAudioRepository(get()) }
        factory { params -> buildAbsoluteSessionRepository(params.get()) }
        factory { buildAuthRepository(get(), get()) }
    }

private fun buildAuthManager(auth: Auth): IAuthManager = AuthManager(auth)

val sourceModule =
    module {
        factory { buildAuthManager(get()) }
    }

private fun buildSupabaseClient(): SupabaseClient =
    createSupabaseClient(
        supabaseUrl = "https://sudtpqyfweqfbfhifjlu.supabase.co", // TODO: Fix hardcode values
        supabaseKey =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InN1" +
                "ZHRwcXlmd2VxZmJmaGlmamx1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTY1NzczOTYsImV4cC" +
                "I6MjA3MjE1MzM5Nn0.8LclhHt2mDDRQh_SxnBBw23NCRlYewb04FZzPOi3b2Y",
    ) {
        install(Auth) {
            host = "com.amontdevs.bluefrog"
        }
        install(ComposeAuth) {
            googleNativeLogin("923467140196-kujcnj1c5jnqvfvhuo2u573ob8s59jtv.apps.googleusercontent.com")
        }
    }

private fun getComposeAuth(supabaseClient: SupabaseClient) = supabaseClient.composeAuth

private fun getAuth(supabaseClient: SupabaseClient) = supabaseClient.auth

val clientModule =
    module {
        single { buildSupabaseClient() }
        single { getComposeAuth(get()) }
        single { getAuth(get()) }
    }
