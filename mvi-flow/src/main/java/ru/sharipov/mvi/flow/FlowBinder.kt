package ru.sharipov.mvi.flow

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.sharipov.mvi.core.event.Event
import ru.sharipov.mvi.core.middleware.Middleware
import ru.sharipov.mvi.core.reducer.Reactor

/**
 * An object that binds together everything in coroutineScope and logs events
 */
interface FlowBinder {

    fun <T : Event, SH> CoroutineScope.bind(
        eventHub: FlowEventHub<T>,
        middleware: Middleware<T, Flow<T>, Flow<T>>,
        stateHolder: SH,
        reactor: Reactor<T, SH>
    ) {
        val eventFlow = eventHub.observe()
            .onEach { event: T ->
                Log.d(TAG, event.toString())
                reactor.react(stateHolder, event)
            }.catch {
                Log.e(TAG, it.message, it)
                throw it
            }.shareIn(this, SharingStarted.Eagerly)
        this@bind.launch {
            middleware.transform(eventFlow)
                .collect { transformedEvent: T ->
                    eventHub.emit(transformedEvent)
                }
        }
    }

    companion object {
        const val TAG = "FlowBinder"
    }
}