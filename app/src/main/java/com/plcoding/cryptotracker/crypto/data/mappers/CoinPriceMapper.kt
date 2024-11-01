package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.plcoding.cryptotracker.crypto.domain.model.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        time = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.of("UTC"))
    )
}