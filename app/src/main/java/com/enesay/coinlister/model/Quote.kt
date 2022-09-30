package com.enesay.coinlister.model

import androidx.room.Embedded
import androidx.room.Entity

    @Entity
    data class Quote(
        @Embedded
        val USD: USD
    ) {

    }