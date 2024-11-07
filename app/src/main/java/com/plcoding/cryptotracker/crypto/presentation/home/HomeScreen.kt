package com.plcoding.cryptotracker.crypto.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.plcoding.cryptotracker.core.navigation.AdaptiveCoinListDetailPane
import com.plcoding.cryptotracker.core.navigation.ChatRoute
import com.plcoding.cryptotracker.core.navigation.CoinListDetailRoute
import com.plcoding.cryptotracker.core.navigation.SettingRoute
import com.plcoding.cryptotracker.core.navigation.util.appComposable
import com.plcoding.cryptotracker.crypto.presentation.chat.ChatScreen
import com.plcoding.cryptotracker.crypto.presentation.model.BottomNavigationItem
import com.plcoding.cryptotracker.crypto.presentation.setting.SettingScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(

            ) {
                BottomNavigationItem.items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = state.selectedIndex == index,
                        onClick = {
                            viewModel.onAction(HomeAction.OnBottomNavigationItemClick(index))
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (state.selectedIndex == index) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = CoinListDetailRoute
            ) {
                appComposable<CoinListDetailRoute> {
                    AdaptiveCoinListDetailPane()
                }
                appComposable<ChatRoute> {
                    ChatScreen()
                }
                appComposable<SettingRoute> {
                    SettingScreen()
                }
            }
        }
    }
}