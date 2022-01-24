package ru.surfstudio.mvi.flow.app.network

import kotlinx.serialization.Serializable

@Serializable
data class IpResponse(
    val country: String
)
