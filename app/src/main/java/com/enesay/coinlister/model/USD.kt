package com.enesay.coinlister.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class USD(
    @ColumnInfo
    val last_updated: String,
    @ColumnInfo
    val market_cap: Double,
    @ColumnInfo
    val market_cap_dominance: Double,
    @ColumnInfo
    val percent_change_1h: Double,
    @ColumnInfo
    val percent_change_24h: Double,
    @ColumnInfo
    val percent_change_7d: Double,
    @ColumnInfo
    val price: Double,
    @ColumnInfo
    val volume_24h: Double,
    @ColumnInfo
    val volume_change_24h: Double
) {
}