package ru.surfstudio.mvi.flow.app.simple

import ru.surfstudio.mvi.core.reducer.Reducer
import ru.surfstudio.mvi.flow.app.request.RequestState

data class SimpleState(
    val title: String = "Кликай меня полностью",
    val counter: Int = 42,
    val request: RequestState = RequestState.None
)

class SimpleReducer: Reducer<SimpleEvent, SimpleState> {

    override fun reduce(state: SimpleState, event: SimpleEvent): SimpleState {
        return when(event) {
            is SimpleEvent.SimpleClick -> state.copy(title = "Клик")
            is SimpleEvent.TitleUpdate -> state.copy(title = event.title)
            is SimpleEvent.IncrementClick -> state.copy(counter = state.counter + 1)
            is SimpleEvent.DecrementClick -> state.copy(counter = state.counter - 1)
            is SimpleEvent.RequestEvent -> state.copy(request = event.request)
            else -> state
        }
    }
}