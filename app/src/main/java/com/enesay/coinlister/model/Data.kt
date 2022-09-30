package com.enesay.coinlister.model

import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity()

data class Data(

    @SerializedName("id")
    @ColumnInfo
    val id: Int,
    @SerializedName("quote")
    @Embedded
    val quote: Quote?,
    @SerializedName("symbol")
    @ColumnInfo
    val symbol: String,
    @SerializedName("name")
    @ColumnInfo
    val name: String,
    @SerializedName("cmc_rank")
    @ColumnInfo
    val cmc_rank: Int,
    @ColumnInfo(defaultValue = "false")
    var kontrol: Boolean,
    @SerializedName("total_supply")
    @ColumnInfo
    var total_supply: Double


    ) {
        @PrimaryKey(autoGenerate = true)
        var uid: Int = 0
    }


    data class relation(
        val data: Data,
        @Relation(
            parentColumn = "uid",
            entityColumn = "fav_id"
        )
        val favorites: favorites
    ) {

    }

    @Entity
    data class favorites(
        @PrimaryKey
        val fav_id: Int,
        @ColumnInfo
        val name: String,
        @ColumnInfo
        val price: Double
    ) {

    }
