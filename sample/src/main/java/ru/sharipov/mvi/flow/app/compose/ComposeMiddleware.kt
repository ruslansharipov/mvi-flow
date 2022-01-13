package ru.sharipov.mvi.flow.app.compose

import kotlinx.coroutines.flow.Flow
import ru.sharipov.mvi.flow.middleware.DslFlowMiddleware

class ComposeMiddleware: DslFlowMiddleware<ComposeEvent> {

    override fun transform(eventStream: Flow<ComposeEvent>): Flow<ComposeEvent> {
        return eventStream.transformations {
            // TODO
        }
    }
}