package com.felix.guotai.remote.model

import com.google.gson.annotations.SerializedName

data class StockValuation(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("PEratio")
    val peRatio: String?,
    @SerializedName("DividendYield")
    val dividend: String?,
    @SerializedName("PBratio")
    val pbRatio: String
)