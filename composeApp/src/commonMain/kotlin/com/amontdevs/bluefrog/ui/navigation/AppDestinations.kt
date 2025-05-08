package com.amontdevs.bluefrog.ui.navigation

enum class AppDestinations(
    val route: String,
    val isBottomBarItem: Boolean,
) {
    HOME_ROUTE("home", true),
    STATS_ROUTE("stats", true),
    SOCIAL_ROUTE("social", true),
    USER_ROUTE("user", true),
    ABSOLUTE_SESSION_ROUTE("absolute_session", false),
    ;

    fun getBottomBarItem(): BottomNavigationItem? =
        when (this) {
            HOME_ROUTE -> BottomNavigationItem.HOME
            STATS_ROUTE -> BottomNavigationItem.STATS
            SOCIAL_ROUTE -> BottomNavigationItem.SOCIAL
            USER_ROUTE -> BottomNavigationItem.USER
            else -> null
        }

    companion object {
        fun geDestinationFromAppBottom(bottomNavigationItem: BottomNavigationItem): AppDestinations =
            when (bottomNavigationItem) {
                BottomNavigationItem.HOME -> HOME_ROUTE
                BottomNavigationItem.STATS -> STATS_ROUTE
                BottomNavigationItem.SOCIAL -> SOCIAL_ROUTE
                BottomNavigationItem.USER -> USER_ROUTE
            }
    }
}
