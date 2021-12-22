package ru.sharipov.mvi.core.reducer

import ru.sharipov.mvi.core.event.Event

/**
 * Class reacting on state changes
 *
 * [E] - event type
 * [H] - state holder type
 */
interface Reactor<E : Event, H> {

    /**
     * Реакция на событие
     *
     * @param sh StateHolder, который содержит логику смены состояния
     * @param event событие, на которое реагирует reactor
     */
    fun react(sh: H, event: E)
}