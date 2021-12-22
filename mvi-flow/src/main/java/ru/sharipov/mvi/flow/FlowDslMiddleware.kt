package ru.sharipov.mvi.flow.middleware

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import ru.sharipov.mvi.flow.EventTransformerList
import ru.sharipov.mvi.core.event.Event
import ru.sharipov.mvi.core.middleware.DslMiddleware

/**
 * [Middleware] with Flow and DSL support with [EventTransformerList].
 */
interface DslFlowMiddleware<T : Event> : DslMiddleware<T, Flow<T>, Flow<T>, EventTransformerList<T>> {

    override fun provideTransformationList(eventStream: Flow<T>): EventTransformerList<T> {
        return EventTransformerList(eventStream)
    }

    override fun combineTransformations(transformations: List<Flow<T>>): Flow<T> {
        return merge(*transformations.toTypedArray())
    }
}