package com.plcoding.cryptotracker.crypto.data.networking

import com.plcoding.core.data.networking.constructUrl
import com.plcoding.core.data.networking.safeCall
import com.plcoding.core.domain.util.NetworkError
import com.plcoding.core.domain.util.Result
import com.plcoding.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinResponseDto
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.domain.model.Coin
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}