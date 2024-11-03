package com.plcoding.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.plcoding.cryptotracker.core.navigation.AdaptiveCoinListDetailPane
import com.plcoding.cryptotracker.core.navigation.CoinListDetailRoute
import com.plcoding.cryptotracker.core.navigation.SplashRoute
import com.plcoding.cryptotracker.core.navigation.util.appComposable
import com.plcoding.cryptotracker.core.navigation.util.popAndNavigate
import com.plcoding.cryptotracker.crypto.presentation.splash.SplashScreen
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()
        hideSystemBars()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = SplashRoute
                        ) {
                            appComposable<SplashRoute> {
                                SplashScreen(
                                    onFinish = {
                                        navController.popAndNavigate(CoinListDetailRoute)
                                    }
                                )
                            }
                            appComposable<CoinListDetailRoute> {
                                AdaptiveCoinListDetailPane()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
    }
}