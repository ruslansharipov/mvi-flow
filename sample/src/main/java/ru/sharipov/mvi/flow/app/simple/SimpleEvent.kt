package ru.sharipov.mvi.flow.app.simple

import ru.sharipov.mvi.core.event.Event
import ru.sharipov.mvi.flow.app.request.RequestState

sealed class SimpleEvent: Event {

    object SimpleClick: SimpleEvent()
    object IncrementClick : SimpleEvent()
    object DecrementClick : SimpleEvent()

    object StartLoadingClick : SimpleEvent()

    data class RequestEvent(val request: RequestState): SimpleEvent()

    data class TitleUpdate(val title: String): SimpleEvent()
}