package ru.surfstudio.mvi.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.surfstudio.mvi.core.state.MutableState

/**
 * State implementing with Coroutines Flow, that could be observed and changed.
 */
class FlowState<S>(initialState: S): MutableState<S, Flow<S>> {

    private val mutableFlow = MutableStateFlow(initialState)

    override val currentState: S
        get() = mutableFlow.value

    override fun emitNewState(newState: S) {
        mutableFlow.value = newState
    }

    override fun observeState(): Flow<S> {
        return mutableFlow
    }
}