package ru.sharipov.mvi.flow.app.simple

import ru.sharipov.mvi.flow.FlowEventHub
import ru.sharipov.mvi.flow.FlowState
import ru.sharipov.mvi.flow.lifecycle.MviViewModel

class SimpleViewModel : MviViewModel<SimpleState, SimpleEvent>() {

    override val state: FlowState<SimpleState> = FlowState(SimpleState())
    override val hub: FlowEventHub<SimpleEvent> = FlowEventHub()
    override val middleware: SimpleMiddleware = SimpleMiddleware(state)
    override val reducer: SimpleReducer = SimpleReducer()

    init {
        init()
    }
}