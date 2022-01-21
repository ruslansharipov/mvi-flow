package ru.surfstudio.mvi.flow.app.simple

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import ru.surfstudio.mvi.flow.FlowState
import ru.surfstudio.mvi.flow.app.request.RequestState
import ru.surfstudio.mvi.flow.DslFlowMiddleware
import java.io.IOException

class SimpleMiddleware(
    private val state: FlowState<SimpleState>
) : DslFlowMiddleware<SimpleEvent> {

    override fun transform(eventStream: Flow<SimpleEvent>): Flow<SimpleEvent> {
        return eventStream.transformations {
            addAll(
                SimpleEvent.StartLoadingClick::class streamMapTo { requestFlow(it) },
                SimpleEvent.SimpleClick::class streamMapTo { clicks -> clicksFlow(clicks) }
            )
        }
    }

    @OptIn(FlowPreview::class)
    private fun clicksFlow(clicks: Flow<SimpleEvent.SimpleClick>): Flow<SimpleEvent.TitleUpdate> {
        return clicks.debounce(1000)
            .map { SimpleEvent.TitleUpdate("Ты перестал кликать") }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun requestFlow(flow: Flow<SimpleEvent.StartLoadingClick>): Flow<SimpleEvent.RequestEvent> {
        return flow.filter { state.currentState.request == RequestState.None }
            .flatMapLatest {
                flow {
                    delay(3000)
                    if (System.currentTimeMillis() % 2 == 0L) {
                        throw IOException()
                    }
                    emit(SimpleEvent.RequestEvent(RequestState.Success))
                }.onStart {
                    emit(SimpleEvent.RequestEvent(RequestState.Loading))
                }.catch {
                    emit(SimpleEvent.RequestEvent(RequestState.Error))
                }.onCompletion {
                    delay(2000)
                    emit(SimpleEvent.RequestEvent(RequestState.None))
                }
            }

    }
}