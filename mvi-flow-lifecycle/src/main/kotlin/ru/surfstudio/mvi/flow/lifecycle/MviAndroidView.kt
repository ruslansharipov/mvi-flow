package ru.surfstudio.mvi.flow.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import ru.surfstudio.mvi.core.event.Event

/**
 * Android object with lifecycle that can emit events to a screens hub and observe state changes
 */
interface MviAndroidView<S : Any, E : Event> : MVIView<S, E>, LifecycleOwner {

    override val uiScope: CoroutineScope
        get() = lifecycleScope
}

interface MVIView<S : Any, E : Event> {

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
    @OptIn(InternalCoroutinesApi::class)
    fun observeState(collector: suspend (S) -> Unit) {
        uiScope.launch(Dispatchers.Main) {
            viewModel.state
                .observeState()
                .collect(FlowCollector { collector(it) })
        }
    }
}