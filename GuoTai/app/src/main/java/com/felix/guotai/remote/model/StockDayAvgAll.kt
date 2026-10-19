package com.felix.guotai.remote.model

import com.google.gson.annotations.SerializedName

data class StockDayAvgAll(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Code")
    val code: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("ClosingPrice")
    val closingPrice: String,
    @SerializedName("MonthlyAveragePrice")
    val monthlyAveragePrice: String
)