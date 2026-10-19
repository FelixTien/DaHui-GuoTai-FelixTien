package com.felix.guotai.remote

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.felix.guotai.remote.model.Stock
import com.felix.guotai.remote.model.StockDayAll
import com.felix.guotai.remote.model.StockDayAvgAll
import com.felix.guotai.remote.model.StockValuation
import com.felix.guotai.remote.retrofit.RemoteAPI
import com.felix.guotai.remote.retrofit.RemoteRetrofit

@SuppressLint("StaticFieldLeak")
object Remote {
    private var retrofit: RemoteAPI = RemoteRetrofit.loadDeveloper().create(RemoteAPI::class.java)
    private var stockDayAll: List<StockDayAll> = mutableListOf()
    private var stockDayAvgAll: List<StockDayAvgAll> = mutableListOf()
    private var stockValuation: List<StockValuation> = mutableListOf()
    var stock: MutableList<Stock> = mutableListOf()
    var loaded by mutableStateOf(false)

    private suspend fun loadStockDayAll(){
        if (stockDayAll.isNotEmpty()){
            return
        }
        val response = retrofit.getStockDayAll()
        stockDayAll = if (response.isSuccessful) response.body()!! else return
    }
    private suspend fun loadStockDayAvgAll(){
        if (stockDayAvgAll.isNotEmpty()){
            return
        }
        val response = retrofit.getStockDayAvgAll()
        stockDayAvgAll = if (response.isSuccessful) response.body()!! else return
    }
    private suspend fun loadStockValuation(){
        if (stockValuation.isNotEmpty()){
            return
        }
        val response = retrofit.getStockValuation()
        stockValuation = if (response.isSuccessful) response.body()!! else return
    }
    suspend fun loadStock(){
        loadStockValuation()
        loadStockDayAll()
        loadStockDayAvgAll()
        stockValuation.forEach { valuation ->
            val dayAll = stockDayAll.find { it.code == valuation.code }
            val dayAvgAll = stockDayAvgAll.find { it.code == valuation.code }
            stock.add(Stock(
                date = valuation.date,
                code = valuation.code,
                name = valuation.name,
                peRatio = valuation.peRatio ?: "",
                dividend = valuation.dividend ?: "",
                pbRatio = valuation.pbRatio,
                tradeVolume = dayAll?.tradeVolume ?: "",
                tradeValue = dayAll?.tradeValue ?: "",
                openingPrice = dayAll?.openingPrice ?: "",
                highestPrice = dayAll?.highestPrice ?: "",
                lowestPrice = dayAll?.lowestPrice ?: "",
                closingPrice = dayAll?.closingPrice ?: "",
                change = dayAll?.change ?: "",
                transaction = dayAll?.transaction ?: "",
                monthlyAveragePrice = dayAvgAll?.monthlyAveragePrice ?: "",
            ))
        }
        loaded = true
    }
}