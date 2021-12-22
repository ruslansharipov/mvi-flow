package ru.sharipov.mvi.core.hub

import ru.sharipov.mvi.core.event.Event

/**
 * Basic typed EventHub.
 * It's a bus for emitting and observing events.
 *
 * @param T             event type
 * @param EventStream   type of event stream
 */
interface EventHub<T : Event, EventStream> : ImmutableHub<EventStream>, MutableHub<T>