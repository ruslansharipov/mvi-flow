package ru.surfstudio.mvi.core.hub

import ru.surfstudio.mvi.core.event.Event

interface MutableHub<T : Event> {
    /**
     * Emitting [event] to the hub
     */
    suspend fun emit(event: T)
}