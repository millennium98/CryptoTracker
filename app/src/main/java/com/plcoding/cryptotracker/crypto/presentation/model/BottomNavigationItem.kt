package com.plcoding.cryptotracker.crypto.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.plcoding.cryptotracker.core.navigation.ChatRoute
import com.plcoding.cryptotracker.core.navigation.CoinListDetailRoute
import com.plcoding.cryptotracker.core.navigation.SettingRoute

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: Any
) {

    companion object {
        val items = listOf(
            BottomNavigationItem(
                title = "Home",
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasNews = false,
                route = CoinListDetailRoute
            ),
            BottomNavigationItem(
                title = "Chat",
                selectedIcon = Icons.AutoMirrored.Filled.Chat,
                unselectedIcon = Icons.AutoMirrored.Outlined.Chat,
                hasNews = false,
                route = ChatRoute
            ),
            BottomNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Default.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                hasNews = false,
                route = SettingRoute
            )
        )
    }
}
