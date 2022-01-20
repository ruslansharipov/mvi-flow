package ru.sharipov.mvi.flow.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.sharipov.mvi.core.event.Event
import ru.sharipov.mvi.core.reducer.Reducer
import ru.sharipov.mvi.flow.FlowBinder
import ru.sharipov.mvi.flow.FlowEventHub
import ru.sharipov.mvi.flow.FlowState
import ru.sharipov.mvi.flow.middleware.DslFlowMiddleware

/**
 * An interface of ViewModel providing implementations of observable
 * state and hub of events based on Coroutines Flow
 */
abstract class MviViewModel<S : Any, E : Event> : ViewModel(), FlowBinder {

    abstract val state: FlowState<S>
    abstract val hub: FlowEventHub<E>
    abstract val reducer: Reducer<E, S>
    abstract val middleware: DslFlowMiddleware<E>

    /** Must be called in descendant class `init` */
    fun init() {
        viewModelScope.bind(hub, middleware, state, reducer)
    }
}