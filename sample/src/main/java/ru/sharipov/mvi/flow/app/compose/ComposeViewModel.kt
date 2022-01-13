package ru.sharipov.mvi.flow.app.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.sharipov.mvi.flow.FlowBinder
import ru.sharipov.mvi.flow.FlowEventHub
import ru.sharipov.mvi.flow.FlowState
import ru.sharipov.mvi.flow.MviViewModel

class ComposeViewModel: ViewModel(), MviViewModel<ComposeState, ComposeEvent>, FlowBinder {

        override val state: FlowState<ComposeState> = FlowState(ComposeState())
        override val hub: FlowEventHub<ComposeEvent> = FlowEventHub()
        private val middleware: ComposeMiddleware = ComposeMiddleware()
        private val reducer: ComposeReducer = ComposeReducer()

        init {
            viewModelScope.bind(hub, middleware, state, reducer)
        }
}