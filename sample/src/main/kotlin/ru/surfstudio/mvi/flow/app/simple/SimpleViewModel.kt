package ru.surfstudio.mvi.flow.app.simple

import ru.surfstudio.mvi.flow.FlowEventHub
import ru.surfstudio.mvi.flow.FlowState
import ru.surfstudio.mvi.flow.lifecycle.MviViewModel

class SimpleViewModel : MviViewModel<SimpleState, SimpleEvent>() {

    override val state: FlowState<SimpleState> = FlowState(SimpleState())
    override val hub: FlowEventHub<SimpleEvent> = FlowEventHub()
    override val middleware: SimpleMiddleware = SimpleMiddleware(state)
    override val reducer: SimpleReducer = SimpleReducer()

    init {
        init()
    }
}