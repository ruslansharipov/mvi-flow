package ru.surfstudio.mvi.core.state

/**
 * State that could be modified by emitting new value
 */
interface MutableState<State, ImmutableStateStream> : ImmutableState<State, ImmutableStateStream> {

    /**
     * Emits [newState] and notifies all subscribers
     */
    fun emitNewState(newState: State)
}