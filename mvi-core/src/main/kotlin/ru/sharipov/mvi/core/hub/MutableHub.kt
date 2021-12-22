package ru.sharipov.mvi.core.hub

import ru.sharipov.mvi.core.event.Event

interface MutableHub<T : Event> {
    /**
     * Emitting [event] to the hub
     */
    suspend fun emit(event: T)
}