package com.plcoding.cryptotracker.crypto.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnBottomNavigationItemClick -> {
                selectBottomNavigationItem(action.index)
            }
        }
    }

    private fun selectBottomNavigationItem(index: Int) {
        _state.update { it.copy(selectedIndex = index) }

    }
}