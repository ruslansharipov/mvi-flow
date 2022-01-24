package ru.surfstudio.mvi.flow.app.network

import retrofit2.http.GET

interface IpApi {

    @GET("json")
    suspend fun getIp(): IpResponse
}