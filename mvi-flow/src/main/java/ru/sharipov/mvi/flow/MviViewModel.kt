package ru.sharipov.mvi.flow

import ru.sharipov.mvi.core.event.Event

/**
 * An interface of ViewModel providing implementations of observable
 * state and hub of events based on Coroutines Flow
 */
interface MviViewModel<S: Any, E : Event> {
    val state: FlowState<S>
    val hub: FlowEventHub<E>
}