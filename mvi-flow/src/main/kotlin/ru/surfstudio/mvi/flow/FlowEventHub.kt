package ru.surfstudio.mvi.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.surfstudio.mvi.core.event.Event
import ru.surfstudio.mvi.core.hub.EventHub

class FlowEventHub<T : Event> : EventHub<T, Flow<T>> {

    private val hubFlow = MutableSharedFlow<T>()

    override fun observe(): Flow<T> {
        return hubFlow
    }

    override suspend fun emit(event: T) {
        hubFlow.emit(event)
    }
}