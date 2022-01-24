package ru.surfstudio.mvi.flow.app.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IpRepository(private val api: IpApi) {

    fun getIpCountry(): Flow<String> = flow {
        Thread.sleep(2000L) // test delay
        emit(api.getIp().country)
    }.flowOn(Dispatchers.IO)
}