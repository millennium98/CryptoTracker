package com.plcoding.cryptotracker.crypto.domain

import com.plcoding.core.domain.util.NetworkError
import com.plcoding.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.model.Coin

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}