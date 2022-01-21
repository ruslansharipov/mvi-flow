package ru.surfstudio.mvi.flow.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.surfstudio.mvi.core.event.Event
import ru.surfstudio.mvi.core.reducer.Reducer
import ru.surfstudio.mvi.flow.FlowBinder
import ru.surfstudio.mvi.flow.FlowEventHub
import ru.surfstudio.mvi.flow.FlowState
import ru.surfstudio.mvi.flow.DslFlowMiddleware

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