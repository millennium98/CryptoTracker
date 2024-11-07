package com.plcoding.cryptotracker.core.presentation.util

//@Composable
//inline fun <reified T : ViewModel> NavBackStackEntry.navGraphViewModel(navController: NavController): T {
//    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
//    val parentEntry = remember(this) { navController.getBackStackEntry(navGraphRoute) }
//    return koinNavViewModel<T>(viewModelStoreOwner = parentEntry)
//}