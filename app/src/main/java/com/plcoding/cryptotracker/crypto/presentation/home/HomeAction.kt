package com.plcoding.cryptotracker.crypto.presentation.home

sealed interface HomeAction {
    data class OnBottomNavigationItemClick(val index: Int) : HomeAction
}