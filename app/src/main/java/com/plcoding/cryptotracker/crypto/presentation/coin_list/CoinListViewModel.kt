package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.coin_detail.DataPoint
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUI
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(coinUI = action.coinUI)
            }
            is CoinListAction.OnDetailBackPress -> {
                deselectCoin()
            }
        }
    }

    private fun deselectCoin() {
        viewModelScope.launch {
            _state.update {
                it.copy(selectedCoin = null)
            }
        }
    }

    private fun selectCoin(coinUI: CoinUI) {
        viewModelScope.launch {
            _state.update {
                it.copy(selectedCoin = coinUI)
            }
            withContext(Dispatchers.IO) {
                coinDataSource.getCoinHistory(
                    coinId = coinUI.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                    .onSuccess { history ->
                        val dataPoints = withContext(Dispatchers.Default) {
                            history
                                .sortedBy { it.time }
                                .map {
                                    DataPoint(
                                        x = it.time.hour.toFloat(),
                                        y = it.priceUsd.toFloat(),
                                        xLabel = DateTimeFormatter
                                            .ofPattern("ha\nM/d")
                                            .format(it.time)
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    selectedCoin = it.selectedCoin?.copy(
                                        coinPriceHistory = dataPoints
                                    )
                                )
                            }
                        }
                    }
                    .onError { error ->
                        withContext(Dispatchers.Main) {
                            _events.send(CoinListEvent.Error(error))
                        }
                    }
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            withContext(Dispatchers.IO) {
                coinDataSource
                    .getCoins()
                    .onSuccess { coins ->
                        withContext(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    coins = coins.map { coin -> coin.toCoinUI() }
                                )
                            }
                        }
                    }
                    .onError { error ->
                        withContext(Dispatchers.Main) {
                            _state.update {
                                it.copy(isLoading = false)
                            }
                            _events.send(CoinListEvent.Error(error))
                        }
                    }
            }
        }
    }
}