package com.plcoding.cryptotracker.crypto.domain

import com.plcoding.core.domain.util.NetworkError
import com.plcoding.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.model.Coin
import com.plcoding.cryptotracker.crypto.domain.model.CoinPrice
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}