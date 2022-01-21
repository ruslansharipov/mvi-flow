package ru.surfstudio.mvi.core.state

/**
 * State that could not be modified, only observed
 *
 * [State] - type of screen state
 * [ImmutableStateStream] - stream of state updates that could not be modified
 */
interface ImmutableState<State, ImmutableStateStream> {

    val currentState: State

    fun observeState() : ImmutableStateStream
}