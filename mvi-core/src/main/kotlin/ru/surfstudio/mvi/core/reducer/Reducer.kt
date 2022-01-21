package ru.surfstudio.mvi.core.reducer

import ru.surfstudio.mvi.core.event.Event
import ru.surfstudio.mvi.core.state.MutableState

/**
 * [Reducer] in terms of `Redux`:
 *
 * This entity is responsible to react on given [Event]
 * and produce new screen [State] as result of reaction.
 *
 * [Reducers documentation](https://redux.js.org/basics/reducers)
 */
interface Reducer<E : Event, State> : Reactor<E, MutableState<State, *>> {

    override fun react(sh: MutableState<State, *>, event: E) {
        val oldState = sh.currentState
        val newState = reduce(oldState, event)
        if (isStateChanged(oldState, newState)) {
            sh.emitNewState(newState)
        }
    }

    /**
     * Used to check: if screen [State] has changed or not, after reaction on some [Event].
     *
     * So we can't skip screen rendering if [State] hasn't changed.
     *
     * Can be overridden if required some special checks or custom behaviour.
     */
    fun isStateChanged(oldState: State, newState: State) = oldState != newState

    /**
     * Transformation of current screen [State] by given [Event].
     *
     * @return updated screen [State].
     */
    fun reduce(state: State, event: E): State
}