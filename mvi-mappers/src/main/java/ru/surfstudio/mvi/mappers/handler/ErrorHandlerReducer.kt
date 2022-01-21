package ru.surfstudio.mvi.mappers.handler

import ru.surfstudio.mvi.core.event.Event
import ru.surfstudio.mvi.core.reducer.Reducer

/** [Reducer] implementation which is able to handle errors */
interface ErrorHandlerReducer<E : Event, State> : Reducer<E, State> {
    val errorHandler: ErrorHandler
}