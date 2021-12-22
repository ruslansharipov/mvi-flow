package ru.sharipov.mvi.flow.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.sharipov.mvi.core.event.Event
import ru.sharipov.mvi.flow.MviViewModel

/**
 * Android object with lifecycle that can emit events to a screens hub and observe state changes
 */
interface MviAndroidView<S: Any, E : Event> : MVIView<S, E>, LifecycleOwner {

    override val uiScope: CoroutineScope
        get() = lifecycleScope
}

interface MVIView<S: Any, E : Event> {

    /**
     * Scope to observe on state changes and to emit events
     */
    val uiScope: CoroutineScope

    /**
     * viewModel providing event hub for event emission and observable state
     */
    val viewModel: MviViewModel<S, E>

    /**
     * Emits [event] to a hub which is provided by [viewModel]
     */
    fun emit(event: E) {
        uiScope.launch {
            viewModel.hub.emit(event)
        }
    }

    /**
     * Subscribes to state updates with the [collector]
     */
    fun observeState(collector: suspend (S) -> Unit) {
        uiScope.launch(Dispatchers.Main) {
            viewModel.state
                .observeState()
                .collect { collector(it) }
        }
    }
}