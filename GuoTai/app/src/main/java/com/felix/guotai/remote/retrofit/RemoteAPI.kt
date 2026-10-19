package com.felix.guotai.remote.retrofit

import com.felix.guotai.remote.model.StockDayAvgAll
import com.felix.guotai.remote.model.StockDayAll
import com.felix.guotai.remote.model.StockValuation
import retrofit2.Response
import retrofit2.http.GET

interface RemoteAPI {
    @GET("v1/exchangeReport/STOCK_DAY_ALL")
    suspend fun getStockDayAll(): Response<List<StockDayAll>>
    @GET("v1/exchangeReport/STOCK_DAY_AVG_ALL")
    suspend fun getStockDayAvgAll(): Response<List<StockDayAvgAll>>
    @GET("v1/exchangeReport/BWIBBU_ALL")
    suspend fun getStockValuation(): Response<List<StockValuation>>
}