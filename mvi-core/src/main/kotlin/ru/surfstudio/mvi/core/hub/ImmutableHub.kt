package ru.surfstudio.mvi.core.hub

interface ImmutableHub<EventStream> {
    /**
     * @return the stream of events of the hub
     */
    fun observe(): EventStream
}