package ru.sharipov.mvi.core.middleware

import ru.sharipov.mvi.core.event.Event

interface DslMiddleware<T : Event, InputStream, OutputStream, TransformList : List<OutputStream>> :
    Middleware<T, InputStream, OutputStream> {

    fun provideTransformationList(eventStream: InputStream): TransformList

    /**
     * Event stream transformation via DSL
     */
    fun InputStream.transformations(eventStreamBuilder: TransformList.() -> Unit): OutputStream {
        val streamTransformers = provideTransformationList(this)
        eventStreamBuilder.invoke(streamTransformers)
        return combineTransformations(streamTransformers)
    }

    /**
     * Combining transformation list for further attaching to the main event stream
     */
    fun combineTransformations(transformations: List<OutputStream>): OutputStream

}