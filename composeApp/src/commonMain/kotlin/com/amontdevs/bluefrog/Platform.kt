package com.amontdevs.bluefrog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
