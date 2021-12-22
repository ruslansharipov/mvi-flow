package ru.sharipov.mvi.flow.app.simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.sharipov.mvi.flow.*
import ru.sharipov.mvi.flow.app.simple.SimpleEvent
import ru.sharipov.mvi.flow.app.simple.SimpleMiddleware
import ru.sharipov.mvi.flow.app.simple.SimpleReducer
import ru.sharipov.mvi.flow.app.simple.SimpleState

class SimpleViewModel : ViewModel(), MviViewModel<SimpleState, SimpleEvent>, FlowBinder {

    override val state: FlowState<SimpleState> = FlowState(SimpleState())
    override val hub: FlowEventHub<SimpleEvent> = FlowEventHub()
    private val middleware: SimpleMiddleware = SimpleMiddleware(state)
    private val reducer: SimpleReducer = SimpleReducer()

    init {
        viewModelScope.bind(hub, middleware, state, reducer)
    }
}